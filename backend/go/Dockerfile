FROM golang:1.24 AS build

WORKDIR /app
COPY ./go ./go
COPY ./protobuf ./protobuf
WORKDIR /app/go

RUN apt update && apt install protobuf-compiler -y && \
    go install google.golang.org/protobuf/cmd/protoc-gen-go@latest && \
    go install google.golang.org/grpc/cmd/protoc-gen-go-grpc@latest && \
    go generate ./... && \
    go build -o book-exchange-app ./cmd/server

FROM ubuntu:latest
COPY --from=build /app/go/book-exchange-app /book-exchange-app

CMD [ "/book-exchange-app" ]
