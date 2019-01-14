import React, { Component } from 'react';
import styles from './App.scss';
import IndexContainer from './container/IndexContainer'
import LoginContainer from './container/LoginContainer'
import { BrowserRouter as Router, Route, Link } from 'react-router-dom'

class App extends Component {
  renderRouter(){
    return <Router>
      <div>
          <Route exact path="/" component={IndexContainer}/>
          <Route path="/login" component={LoginContainer}/>
      </div>
    </Router>
  }
  render() {
    return <div className={styles.appContainer}>
        {this.renderRouter()}
    </div>
  }
}

export default App;
