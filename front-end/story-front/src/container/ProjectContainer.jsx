import React from 'react'
import styles from './ProjectContainer.scss'
import { BrowserRouter as Router, Route, Link, Redirect, Switch } from 'react-router-dom'
import BoardContainer from './BoardContainer'
import SettingContainer from './SettingContainer'
import CreateProjectContainer from './CreateProjectContainer'

const TAB_NAME = ['Story Mapping', '项目设置']
class ProjectContainer extends React.Component{
    state = {
        currentTab: TAB_NAME[0],
    }
    componentDidMount(){
        this.fixTab()
    }
    componentDidUpdate(){
        this.fixTab()
    }
    handleChangeTab = (tab) => {
        this.setState({
            currentTab: TAB_NAME[tab],
        })
        if (tab == 0){
            this.props.history.push('/index')
            return
        }
        if (tab == 1){
            this.props.history.push('/index/settings')
            return
        }
    }
    fixTab = () => {
        let path = this.props.history.location.pathname.split('/')
        if (path.length > 2 && this.state.currentTab !== TAB_NAME[1]){
            this.setState({
                currentTab: TAB_NAME[1]
            })
        }
        else if (path.length == 2 && this.state.currentTab !== TAB_NAME[0]){
            this.setState({
                currentTab: TAB_NAME[0]
            })
        }
    }
    render(){
        let creating = false
        let path = this.props.history.location.pathname.split('/')
        if (path[path.length - 1] == 'create'){
            creating = true
        }
        return <div className={styles.projectContainer}>
            {
                !creating && <div className={styles.header}>
                    <div className={styles.projectName}>demo</div>
                    <div onClick={this.handleChangeTab.bind(null, 0)} className={styles.block} style={this.state.currentTab == TAB_NAME[0] ? { borderBottom: '3px solid #3798e9', color: '#3798e9'} : {}}>Story Mapping</div>
                    <div onClick={this.handleChangeTab.bind(null, 1)} className={styles.block} style={this.state.currentTab == TAB_NAME[1] ? { borderBottom: '3px solid #3798e9', color: '#3798e9'} : {}}>项目设置</div>
                </div>
            }

            <div className={styles.content}>
                <Route exact path="/index/" component={BoardContainer}/>
                <Route path="/index/settings" component={SettingContainer}/>
                <Route path="/index/create" component={CreateProjectContainer}></Route>
            </div>
        </div>
    }
}

export default ProjectContainer