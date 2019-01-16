import React, { Component } from 'react';
import styles from './App.scss';
import IndexContainer from './container/IndexContainer'
import LoginContainer from './container/LoginContainer'
import { BrowserRouter as Router, Route, Link, Redirect, Switch } from 'react-router-dom'
import 'antd/dist/antd.css'
class App extends Component {
  renderRouter(){
    return <Router>
      <div>
        <Switch>
            <Route path="/login" component={LoginContainer}/>
            <Route path="/index" component={IndexContainer}/>
            <Redirect exact from="/" to="/login"/>
        </Switch>
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
