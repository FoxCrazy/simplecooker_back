import React, { Component } from "react";
import RecipeDataService from "../services/recipe.service";
import { Link } from "react-router-dom";

export default class AddIngredient extends Component {
  
  constructor(props) {
    super(props);
    this.onChangeTitle = this.onChangeTitle.bind(this);
    this.onChangeCalories = this.onChangeCalories.bind(this);
    this.saveIngredient = this.saveIngredient.bind(this);
    this.newIng = this.newIng.bind(this);

    this.state = {
      id: null,
      title: "",
      calories: 0,
      getError: false,
      submitted: false,
      currentCalories: 20,
    }; 
  }

  onChangeTitle(e) {
    this.setState({
      title: e.target.value,
    });
  }

  onChangeCalories(e) {
    this.setState({
      calories: e.target.value,
    });
  }

  saveIngredient() {
    var data = {
      ingredientName: this.state.title,
      calories: this.state.calories,
    };
    console.log(data);
    RecipeDataService.createIng(data)
      .then((response) => {
        this.setState({
          //id: response.data.id,
          ingredientName: response.data.ingredientName,
          calories: response.data.calories,

          submitted: true,
          getError: false,
        });
        console.log(response.data);
      })
      .catch((e) => {
        this.setState({
          getError: true,
        });
        console.log(this.state.getError + "error");
        console.log(e);
      });
  }

  newIng() {
    this.setState({
      id: null,
      title: "",
      calories: 0,
      submitted: false,
    });
  }
  

  render() {
    //const { } = this.state;
    return (
      <div className="submit-form">
        {this.state.submitted ? (
          <div>
            <h4>Your ingredient saved successfully!</h4>

            <button className="btn btn-success choose" onClick={this.newIng}>
              Add new one
            </button>
            <Link
                to={"/recipes"}
                className="btn btn-success choose"
              >
                Go to recipes
              </Link>
          </div>
        ) : (
          <div>
            
            <h3>New ingredient:</h3>

            <div className="form-group">
              <label htmlFor="title">Name</label>
              <input
                type="text"
                className="form-control"
                id="title"
                required
                value={this.state.title}
                onChange={this.onChangeTitle}
                name="title"
              />
            </div>

            <div className="form-group">
              <label htmlFor="calories">Calories per 100 gram</label>
              <input
                type="number"
                className="form-control"
                id="calories"
                required
                value={this.state.calories}
                onChange={this.onChangeCalories}
                name="calories"
              />
            </div>

            <button onClick={this.saveIngredient} className="btn btn-success">
              Upload ingredient
            </button>
          </div>
        )}
      </div>
    );
  }
}
