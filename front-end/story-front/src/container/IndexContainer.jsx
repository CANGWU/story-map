import { Redirect } from 'react-router-dom'
import React from 'react'
import styles from './IndexContainer.scss'
import { Icon, Dropdown, Input, Modal, message } from 'antd'
import indexBg from '../resource/img/indexBg.png'
import ProjectContainer from './ProjectContainer'
import { baseURL, API } from '../config'

class IndexContainer extends React.Component{
    state = {
        ifLogin: true,
        showProjectPopover: false,
        showOptPopover: false,
        localStorage: localStorage,
        projectList: [],
        projectFilter: '',
        currentProject: {},
        invites: [],
    }
    componentWillMount(){
        this.fetchProjectList()
        this.fetchInviteInfo()
    }
    componentDidMount(){
        let jwt = {}
        try {
            jwt = JSON.parse(localStorage.getItem('auth')) || {}
        } catch (e) { console.error(e) }
        if (JSON.stringify(jwt) === '{}'){
            this.props.history.push('/login')
        }
    }
    componentDidUpdate(){
        let jwt = {}
        try {
            jwt = JSON.parse(localStorage.getItem('auth')) || {}
        } catch (e) { console.error(e) }
        if (JSON.stringify(jwt) === '{}'){
            this.props.history.push('/login')
        }
    }
    fetchProjectList = () => {
        // let auth = JSON.parse(localStorage.getItem('auth')) || {}
        API.query(baseURL + '/project/list/my', {
            method: 'POST',
            body: JSON.stringify({
                pageNumber: 0,
                pageSize: 100,
            })
        }).then((json) => {
            if (json.code === 0){
                this.setState({
                    projectList: json.data.content,
                    currentProject: json.data.content.length !== 0 ? json.data.content[0] : {},
                })
            }
        })
    }
    fetchInviteInfo = () => {
        API.query(baseURL + '/member/invite/my', {
            method: 'POST',
            body: JSON.stringify({
                pageNumber: 0,
                pageSize: 99,
            })
        }).then((json) => {
            if (json.code == 0){
                this.setState({
                    invites: json.data.content,
                })
            }
        })
    }
    render(){
        let auth = JSON.parse(localStorage.getItem('auth')) || {}
        let projectContent= (<div className={styles.container}>
            <Input placeholder="输入要搜索的项目名" onChange={(e) => {this.setState({ projectFilter: e.target.value })}} value={this.state.projectFilter}/>
            <div className={styles.pList}>
                {
                    this.state.projectList.filter((v) => v.name.indexOf(this.state.projectFilter) >= 0).map((v, k) => {
                        return <div className={styles.pRow} key={k} onClick={() => {this.setState({ currentProject: v, showProjectPopover: false })}}>{v.name}</div>
                    })
                }
            </div>
            <div className={styles.addBtn} onClick={() => { this.props.history.push('/index/create'); this.setState({ showProjectPopover: false })}}>
                +新建项目
            </div>
        </div>)
        let optContent = (<div className={styles.list}>
            <div className={styles.optRow} onClick={() => { localStorage.clear();this.setState({ localStorage: null })}}>退出</div>
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
                    {
                        this.state.invites.length !== 0 && <div className={styles.mailBox} onClick={() => { this.setState({ showMailModal: true })}}>
                            <Icon type="mail" theme="filled" />
                            <span className={styles.username}>{this.state.invites.length}条新信息</span>
                        </div>
                    }
                    <Dropdown
                        overlay={optContent}
                        overlayClassName={styles.optContent}
                        trigger="click"
                        visible={this.state.showOptPopover}
                        onVisibleChange={(visible) => {this.setState({ showOptPopover: visible })}}
                    >
                        <div className={styles.rightBox}>
                            <span className={styles.username}>{auth.username}</span>
                            <Icon type="down" style={{ transform: `rotate(${this.state.showOptPopover ? 180 : 0}deg)`, transition: 'all 0.3s'}}/>
                        </div>
                    </Dropdown>
                </div>
            </div>
            <div className={styles.content} style={{ backgroundImage: 'url(' + indexBg + ')' }} >
                <ProjectContainer fresh={this.fetchProjectList} project={this.state.currentProject}/>
                {/*<Route path="/index" component={() => {return <ProjectContainer fresh={this.fetchProjectList} project={this.state.currentProject} />}}/>*/}
            </div>
            {
                this.state.showMailModal && <Modal
                    visible
                    title="我的消息"
                    onCancel={() => {this.setState({ showMailModal: false})}}
                    footer={null}
                    width={500}
                    wrapClassName={styles.messageModal}
                >
                    <div className={styles.container}>
                        {
                            this.state.invites.map((v) => {
                                return <div className={styles.inviteRow}>
                                    <div>
                                        <span>您被邀请加入项目</span>
                                        <span className={styles.projectName}>{v.projectName}</span>
                                    </div>
                                    <div className={styles.opt}>
                                        <span className={styles.agree} onClick={() => {
                                            API.query(baseURL + `/member/invite/agree?invitationId=${v.invitationId}`, {
                                                method: 'POST',
                                            }).then((json) => {
                                                if (json.code == 0){
                                                    message.success('操作成功', 0.5)
                                                    this.fetchInviteInfo()
                                                    this.fetchProjectList()
                                                }
                                                else {
                                                    message.success('操作失败')
                                                }
                                            })
                                        }}>同意</span>
                                        <span className={styles.refuse} onClick={() => {
                                            API.query(baseURL + `/member/invite/reject?invitationId=${v.invitationId}`, {
                                                method: 'POST',
                                            }).then((json) => {
                                                if (json.code == 0){
                                                    message.success('操作成功', 0.5)
                                                    this.fetchInviteInfo()
                                                }
                                                else {
                                                    message.success('操作失败')
                                                }
                                            })
                                        }}>拒绝</span>
                                    </div>
                                </div>
                            })
                        }
                    </div>
                </Modal>
            }
        </div>
    }
}

export default IndexContainer
