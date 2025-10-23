package auth

import (
	"testing"
)

func TestGetToken(t *testing.T) {
	tests := []struct {
		user User
	}{
		{User{Id: 1, Login: "peppa", IsAdmin: false}},
		{User{Id: 48, Login: "pep", IsAdmin: true}},
	}

	for _, test := range tests {
		t.Run("Проверка GetToken на отсутствие ошибок", func(t *testing.T) {
			token, err := GetToken(test.user)
			if err != nil || len(token) == 0 {
				t.Errorf("Ошибка в GetToken")
			}
		})
	}
}

func TestGetUserByToken(t *testing.T) {
	tests := []struct {
		user User
	}{
		{User{Id: 1, Login: "peppa", IsAdmin: false}},
		{User{Id: 48, Login: "pep", IsAdmin: true}},
	}

	for _, test := range tests {
		t.Run("Проверка GetToken на отсутствие ошибок", func(t *testing.T) {
			token, err := GetToken(test.user)
			if err != nil || len(token) == 0 {
				t.Errorf("Ошибка в GetToken")
			}
			new_user, err := GetUserByToken(token)
			if err != nil || new_user.Id != test.user.Id || new_user.Login != test.user.Login || new_user.IsAdmin != test.user.IsAdmin {
				t.Errorf("Ошибка в GetUserByToken")
			}
		})
	}
}
