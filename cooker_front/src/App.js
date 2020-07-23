import React, { Component } from "react";
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

import AddRecipe from "./components/add-recipe.component";
import AddIngredient from "./components/add-ingredient.component";
import Recipe from "./components/recipe.component";
import RecipesList from "./components/recipes-list.component";
import IngredientsList from "./components/ingredients-list.component";
import AuthService from "./services/auth.service";

import Login from "./components/login.component";
import Register from "./components/register.component";

class App extends Component {
  constructor(props) {
    super(props);
    this.logOut = this.logOut.bind(this);

    this.state = {
      currentUser: undefined,
    };
  }
  componentDidMount() {
    const user = AuthService.getCurrentUser();
    if (user) {
      this.setState({
        currentUser: user,
      });
    }
  }

  logOut() {
    AuthService.logout();
  }

  render() {
    const { currentUser } = this.state;
    return (
      <Router>
        <div id="mainpart">
          <div className="container mt-3 xs-12">
            <nav id="mainnav" className="navbar navbar-expand navbar-dark">
              <a href="/recipes" className="navbar-brand">
                CookApp
              </a>
              {currentUser ? (
                <div className="navbar-nav mx-auto  ">
                  <li className="nav-item ">
                    <Link to={"/recipes"} className="nav-link">
                      Recipes
                    </Link>
                  </li>
                  <li className="nav-item ">
                    <Link to={"/ingredients"} className="nav-link">
                      Ingredients
                    </Link>
                  </li>

                  <li className="nav-item">
                    <a href="/login" className="nav-link" onClick={this.logOut}>
                      Exit
                    </a>
                  </li>
                </div>
              ) : (
                <div className="navbar-nav mx-auto  ">
                  <li className="nav-item ">
                    <Link to={"/login"} className="nav-link">
                      Login
                    </Link>
                  </li>

                  
                </div>
              )}
            </nav>

            <Switch>
              <Route exact path={["/", "/Recipes"]} component={RecipesList} />
              <Route exact path="/ingredients" component={IngredientsList} />
              <Route exact path="/add" component={AddRecipe} />
              <Route exact path="/addIng" component={AddIngredient} />
              <Route exact path="/login" component={Login} />
              <Route exact path="/register" component={Register} />
              <Route path="/recipes/:id" component={Recipe} />
            </Switch>
          </div>
        </div>
      </Router>
    );
  }
}

export default App;
