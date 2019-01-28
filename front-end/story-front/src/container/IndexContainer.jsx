import { BrowserRouter as Router, Route, Link, Redirect } from 'react-router-dom'
import React from 'react'
import styles from './IndexContainer.scss'
import { Icon, Dropdown, Input } from 'antd'
import indexBg from '../resource/img/indexBg.png'
import ProjectContainer from './ProjectContainer'
import CreateProjectContainer from './CreateProjectContainer'


class IndexContainer extends React.Component{
    state = {
        ifLogin: true,
        showProjectPopover: false,
        showOptPopover: false,
    }
    render(){
        let projectContent= (<div className={styles.container}>
            <Input placeholder="输入项目名"/>
            <div className={styles.pList}>
                <div className={styles.pRow}>项目1</div>
                <div className={styles.pRow}>项目2</div>
                <div className={styles.pRow}>项目3</div>
            </div>
            <div className={styles.addBtn} onClick={() => { this.props.history.push('/index/create'); this.setState({ showProjectPopover: false })}}>
                +新建项目
            </div>
        </div>)
        let optContent = (<div className={styles.list}>
            <div className={styles.optRow}>退出</div>
        </div>)
        if (!this.state.ifLogin){
            return <Redirect to={{ pathname: "/login" }}/>
        }
        return <div className={styles.indexContainer}>
            <div className={styles.header}>
                <div className={styles.left}>
                    <Icon type="coffee" className={styles.icon}/>
                    <span className={styles.title}>Story-Map</span>
                    <div className={styles.leftTab}>
                        <Dropdown
                            overlay={projectContent}
                            overlayClassName={styles.projectContent}
                            trigger="hover"
                            visible={this.state.showProjectPopover}
                            onVisibleChange={(visible) => {this.setState({ showProjectPopover: visible })}}
                        >
                            <div className={styles.tabBox}>
                                <span>我的项目</span>
                                <Icon type="down" style={{ transform: `rotate(${this.state.showProjectPopover ? 180 : 0}deg)`, transition: 'all 0.3s'}}/>
                            </div>
                        </Dropdown>

                    </div>
                </div>
                <div className={styles.right}>
                    <Dropdown
                        overlay={optContent}
                        overlayClassName={styles.optContent}
                        trigger="click"
                        visible={this.state.showOptPopover}
                        onVisibleChange={(visible) => {this.setState({ showOptPopover: visible })}}
                    >
                        <div className={styles.rightBox}>
                            <span className={styles.username}>username</span>
                            <Icon type="down" style={{ transform: `rotate(${this.state.showOptPopover ? 180 : 0}deg)`, transition: 'all 0.3s'}}/>
                        </div>
                    </Dropdown>

                </div>
            </div>
            <div className={styles.content} style={{ backgroundImage: 'url(' + indexBg + ')' }} >
                <Route path="/index" component={ProjectContainer}/>
            </div>
        </div>
    }
}

export default IndexContainer
