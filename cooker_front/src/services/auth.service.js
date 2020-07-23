import axios from "axios";

const API_URL = "http://localhost:8080/";

class AuthService {
  login(login, password) {
    return axios
      .post(API_URL+"authenticate", {
        login,
        password
      })
      .then(response => {
        if (response.data.token) {
          localStorage.setItem("user", JSON.stringify(response.data));
        }

        return response.data;
      });
  }

  logout() {
    localStorage.removeItem("user");
  }

  register(fullName, email, pwHash) {
    return axios.post(API_URL + "registration", {
      fullName,
      email,
      pwHash
    });
  }

  getCurrentUser() {
    return JSON.parse(localStorage.getItem('user'));;
  }
}

export default new AuthService();