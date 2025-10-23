package auth

import (
	"os"
	"time"

	"github.com/golang-jwt/jwt/v5"
)

type User struct {
	jwt.RegisteredClaims
	Id      int64  `json:"id"`
	Login   string `json:"login"`
	IsAdmin bool   `json:"is_admin"`
}

var jwtKey = []byte(os.Getenv("JWT_KEY"))

func getTokenKey(*jwt.Token) (any, error) {
	return jwtKey, nil
}

func GetToken(user User) (string, error) {
	user.RegisteredClaims = jwt.RegisteredClaims{ExpiresAt: &jwt.NumericDate{Time: time.Now().Add(time.Minute * 40)}}

	token := jwt.NewWithClaims(jwt.SigningMethodHS256, user)

	key, err := getTokenKey(token)
	if err != nil {
		return "", err
	}
	return token.SignedString(key)
}

func GetUserByToken(tokenString string) (*User, error) {
	token, err := jwt.ParseWithClaims(tokenString, &User{}, getTokenKey)
	if err != nil {
		return nil, err
	}
	return token.Claims.(*User), nil
}
