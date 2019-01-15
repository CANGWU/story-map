import { BrowserRouter as Router, Route, Link, Redirect } from 'react-router-dom'
import React from 'react'
import styles from './IndexContainer.scss'

class IndexContainer extends React.Component{
    state = {
        ifLogin: true,
    }
    render(){
        if (!this.state.ifLogin){
            console.log('redirect')
            return <Redirect to={{ pathname: "/login" }}/>
        }
        return <div className={styles.indexContainer}>
            <div className={styles.header}>
                xxx
            </div>
            <div className={styles.content}>

            </div>
        </div>
    }
}

export default IndexContainer
