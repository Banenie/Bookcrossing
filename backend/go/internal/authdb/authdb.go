package authdb

import (
	"context"
	"database/sql"
	"errors"

	_ "github.com/jackc/pgx/v5/stdlib"
	"github.com/jmoiron/sqlx"
)

var db *sqlx.DB
var Err404 = errors.New("пользователь не найден")

func CreateDB(ctx context.Context, url string) error {
	var err error
	db, err = sqlx.ConnectContext(ctx, "pgx", url)
	if err != nil {
		return err
	}

	query := `
	CREATE TABLE IF NOT EXISTS users (
		id SERIAL PRIMARY KEY,
		login TEXT UNIQUE,
		password TEXT,
		is_admin BOOL DEFAULT FALSE
	);`

	_, err = db.ExecContext(ctx, query)
	return err
}

func GetUserByLoginAndPassword(ctx context.Context, login string, password string) (int64, bool, error) {
	var id int64
	var isAdmin bool
	err := db.QueryRowContext(ctx, `SELECT id, is_admin FROM users WHERE login=$1 AND password=$2;`, login, password).Scan(&id, &isAdmin)
	if err != nil {
		if errors.Is(err, sql.ErrNoRows) {
			err = Err404
		}
		return 0, false, err
	}
	return id, isAdmin, nil
}

func CreateNewUser(ctx context.Context, login string, password string) (int64, error) {
	var id int64
	row := db.QueryRowContext(ctx, `SELECT nextval(pg_get_serial_sequence('users', 'id')) AS new_id;`)
	err := row.Scan(&id)
	if err != nil {
		return 0, err
	}

	_, err = db.Exec(`INSERT INTO users(id, login, password) VALUES ($1, $2, $3);`, id, login, password)
	if err != nil {
		return 0, err
	}
	return id, nil
}
