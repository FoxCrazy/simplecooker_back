import React, { Component } from "react";
import RecipeDataService from "../services/recipe.service";
import { Link } from "react-router-dom";

export default class AddRecipe extends Component {
  constructor(props) {
    super(props);
    this.onChangeTitle = this.onChangeTitle.bind(this);
    this.onChangeDescription = this.onChangeDescription.bind(this);
    this.onChangeActions = this.onChangeActions.bind(this);
    this.onChangeSearchIngs = this.onChangeSearchIngs.bind(this);
    this.saveRecipe = this.saveRecipe.bind(this);
    this.newRecipe = this.newRecipe.bind(this);
    this.searchIngs = this.searchIngs.bind(this);
    this.onChangeAddedIngs = this.onChangeAddedIngs.bind(this);

    this.state = {
      id: null,
      name: "",
      description: "",
      actions: "",
      ingredients: [],
      addedIngs: [],
      searchIngs: "",
      getError: false,
      submitted: false,
      currentCalories: 20,
      newRecipeId: 0,
    };
  }

  onChangeTitle(e) {
    this.setState({
      name: e.target.value,
    });
  }

  onChangeDescription(e) {
    this.setState({
      description: e.target.value,
    });
  }

  onChangeActions(e) {
    this.setState({
      actions: e.target.value,
    });
  }

  saveRecipe() {
    let data = {
      name: this.state.name,
      description: this.state.description,
      actions: this.state.actions,
    };

    RecipeDataService.create(data)
      .then((response) => {
        console.log(response);
        this.setState({
          //id: response.data.id,
          name: response.data.name,
          description: response.data.description,
          actions: response.data.actions,
          newRecipeId: response.data.recipeId,
          submitted: true,
          getError: false,
        
        });
        //console.log(this.state.newRecipeId);
        //console.log(response.data);
        //console.log(JSON.stringify(this.state.addedIngs));
        RecipeDataService.addIngs(
          this.state.newRecipeId,
          JSON.stringify(this.state.addedIngs),
        )
          .then((response) => {
            this.setState({
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
      })
      .catch((e) => {
        this.setState({
          getError: true,
        });
        console.log(this.state.getError + "error");
        console.log(e);
      });
  }

  newRecipe() {
    this.setState({
      id: null,
      name: "",
      description: "",
      actions: "",
      addIngs: [],

      submitted: false,
    });
  }
  setActiveIngredient(ingredient) {
    this.state.addedIngs.indexOf(ingredient) === -1
      ? this.state.addedIngs.push(ingredient)
      : console.log(ingredient.ingredientName + " is already added");

    console.log(this.state.addedIngs);
    this.setState({ addedIngs: this.state.addedIngs });
  }
  setDisableIngredient(ingredient) {
    this.state.addedIngs.indexOf(ingredient) !== -1
      ? this.state.addedIngs.splice(this.state.addedIngs.indexOf(ingredient), 1)
      : console.log("unable to delete " + ingredient.ingredientName);

    console.log(this.state.addedIngs);
    this.setState({ addedIngs: this.state.addedIngs });
  }

  onChangeSearchIngs(e) {
    const searchIngs = e.target.value;

    this.setState({
      searchIngs: searchIngs,
    });
  }
  onChangeAddedIngs(e) {
    const addedIngs = e.target.value;

    this.setState({
      addedIngs: addedIngs,
    });
  }
  onChangeGrams(id) {
    return (e) => {
      const changedIngs = this.state.addedIngs.map((i) =>
        i.id === id ? { ...i, grammar: parseInt(e.target.value) } : i
      );
      console.log(changedIngs);
      this.setState({
        addedIngs: changedIngs,
      });
    };
  }
  searchIngs() {
    //console.log(this.state.searchIngs);
    RecipeDataService.findIngsByName(this.state.searchIngs)
      .then((response) => {
        this.setState({
          ingredients: response.data.map((i) => ({ ...i, grammar: 0 })),
        });
        console.log(response.data);
      })
      .catch((e) => {
        console.log(e);
      });
  }
 

  render() {
    const { searchIngs, ingredients, addedIngs, currentIndex } = this.state;
    return (
      <div className="submit-form">
        {this.state.submitted ? (
          <div>
            <h4>Your recipe saved successfully!</h4>

            <button className="btn btn-success" onClick={this.newRecipe}>
              Add new one
            </button>
            <Link to={"/recipes"} className="btn btn-success choose">
              Go to recipes
            </Link>
          </div>
        ) : (
          <div>
            {/* {this.state.getError ? (
              <Toast onClose={() => setShow(false)} show={show} delay={3000} autohide>
                <Toast.Header>
                  
                  <strong className="mr-auto">Ошибка</strong>
                  <small></small>
                </Toast.Header>
                <Toast.Body>Данные не приняты. Проверьте заполнение</Toast.Body>
              </Toast>
            ) : (
              <div></div>
            )} */}
            <h3>New recipe:</h3>

            <div className="form-group">
              <label htmlFor="title">Name</label>
              <input
                type="text"
                className="form-control"
                id="title"
                required
                value={this.state.name}
                onChange={this.onChangeTitle}
                name="title"
              />
            </div>

            <div className="form-group">
              <label htmlFor="description">Short description</label>
              <input
                type="text"
                className="form-control"
                id="description"
                required
                value={this.state.description}
                onChange={this.onChangeDescription}
                name="description"
              />
            </div>

            <div className="form-group ">
              <label htmlFor="actions">Actions</label>
              <textarea
                type="text"
                className="form-control"
                id="actions"
                required
                value={this.state.actions}
                onChange={this.onChangeActions}
                name="actions"
                rows="7"
              />
            </div>

            <div className="">
              <h4>Ingredients:</h4>

              <ul id="addedList" className="list-group searched-ings">
                {addedIngs &&
                  addedIngs.map((ingredient, id) => (
                    <li
                      className={
                        "list-group-item list-group-item-warning" +
                        (id === currentIndex ? "active" : "")
                      }
                      onClick={() => {
                        //console.log(ingredient);
                        //this.setDisableIngredient(ingredient);
                      }}
                      key={id}
                    >
                      {" "}
                      <span className="badge float-right badge-danger" onClick={() => {
                        console.log(ingredient);
                        this.setDisableIngredient(ingredient);
                      }}>
                      &#8212;
                      </span>
                      <span className="badge float-right badge-success">
                        {ingredient.calories} calories per 100g
                      </span>
                      {ingredient.ingredientName}
                      <input
                        placeholder="grams"
                        type="number"
                        min="1"
                        //value="100"
                        className="form-control form-control-sm calories float-right"
                        requiredkey={id}
                        onChange={this.onChangeGrams(ingredient.id)}
                      />
                    </li>
                  ))}
              </ul>
              <h6>Add more with search</h6>
              <div className="input-group ">
                <input
                  type="text"
                  className="form-control"
                  placeholder="Search ingredients"
                  value={searchIngs}
                  onChange={this.onChangeSearchIngs}
                />
                <div className="input-group-append">
                  <button
                    className="btn btn-outline-success"
                    type="button"
                    onClick={this.searchIngs}
                  >
                    Search
                  </button>
                </div>
              </div>
              <ul className="list-group">
                {ingredients &&
                  ingredients.map((ingredient, index) => (
                    <li
                      className={
                        "list-group-item list-group-item-action" +
                        (index === currentIndex ? "active" : "")
                      }
                      onClick={() => {
                        console.log(ingredient);
                        this.setActiveIngredient(ingredient);
                      }}
                      key={index}
                    >
                      <span className="badge float-right badge-success">
                        {ingredient.calories} calories per 100g
                      </span>
                      {ingredient.ingredientName}
                      {/* <input placeholder="grams" type="number" min="1"
                        className="form-control form-control-sm calories float-right" requiredkey={index} onChange={this.onChangeTempIng}
                      /> */}
                    </li>
                  ))}
              </ul>
            </div>

            <button onClick={this.saveRecipe} className="btn btn-success">
              Upload recipe
            </button>
          </div>
        )}
      </div>
    );
  }
}
