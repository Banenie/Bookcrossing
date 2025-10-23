package main

import (
	"context"
	"log"
	"os"

	"book-exchange-go-app/internal/authdb"
	"book-exchange-go-app/internal/authserver"
)

func main() {
	err := authdb.CreateDB(context.Background(), os.Getenv("DB_URL"))
	if err != nil {
		log.Fatal("Ошибка во время подключения к БД")
	}

	err = authserver.UserServerServe(os.Getenv("GO_PORT"))
	if err != nil {
		log.Fatal("Ошибка прослушивания пользователя")
	}
}
