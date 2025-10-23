package authserver

//go:generate protoc --go_out=../.. --go-grpc_out=../.. --proto_path=../../.. ../../../protobuf/register.proto

import (
	"context"
	"net"

	"google.golang.org/grpc"
	"google.golang.org/grpc/codes"
	"google.golang.org/grpc/status"

	"book-exchange-go-app/internal/auth"
	"book-exchange-go-app/internal/authdb"
	"book-exchange-go-app/internal/protobuf"
)

type userServer struct {
	protobuf.UnimplementedUserServiceServer
}

func (s *userServer) GetUserByToken(ctx context.Context, token *protobuf.Token) (*protobuf.User, error) {
	user, err := auth.GetUserByToken(token.Token)
	if err != nil {
		return nil, status.Error(codes.Unauthenticated, "ошибка при получении данных пользователя с токена")
	}
	return &protobuf.User{Id: user.Id, Login: user.Login, IsAdmin: user.IsAdmin}, nil
}

func (s *userServer) SignInOrSignUp(ctx context.Context, data *protobuf.UserData) (*protobuf.Token, error) {
	id, isAdmin, err := authdb.GetUserByLoginAndPassword(ctx, data.GetLogin(), data.GetPassword())
	if err != nil {
		if err == authdb.Err404 {
			id, err = authdb.CreateNewUser(ctx, data.GetLogin(), data.GetPassword())
			isAdmin = false
			if err != nil {
				return nil, status.Error(codes.InvalidArgument, "не удалось получить или создать пользователя")
			}
		} else {
			return nil, status.Error(codes.Internal, "ошибка при авторизации")
		}
	}

	token, err := auth.GetToken(auth.User{Id: id, Login: data.GetLogin(), IsAdmin: isAdmin})
	if err != nil {
		return nil, status.Error(codes.Internal, "ошибка при получении токена")
	}
	return &protobuf.Token{Token: token}, nil
}

func UserServerServe(url string) error {
	lis, err := net.Listen("tcp", url)
	if err != nil {
		return err
	}

	grpcServer := grpc.NewServer()
	protobuf.RegisterUserServiceServer(grpcServer, &userServer{})

	if err := grpcServer.Serve(lis); err != nil {
		return err
	}
	return nil
}
