import React from 'react'
import styles from './SettingContainer.scss'
import { Icon, Input, message, Button } from 'antd'
import { baseURL, API} from '../config'
import InviteModal from './modal/InviteModal'

const { TextArea } = Input

class SettingContainer extends React.Component{
    state = {
        currentTab: 0,
        editingName: false,
        editingDes: false,
        editingSign: false,
        name: '',
        sign: '',
        des: '',
        officials: [],
        guests: [],
        invites: [],
        showInviteModal: false,
    }
    componentWillMount(){
        if (JSON.stringify(this.props.project) !== '{}'){
            this.fetchProjectDetail(this.props)
            this.fetchProjectMember(this.props)
            this.fetchInviteList(this.props)
        }
    }
    componentWillReceiveProps(nextProps){
        if (this.props.project.id !== nextProps.project.id){
            this.fetchProjectDetail(nextProps)
            this.fetchProjectMember(nextProps)
            this.fetchInviteList(nextProps)
        }
    }
    fetchProjectDetail = (props) => {
        API.query(baseURL + `/project/${props.project.id}`, {
            method: 'GET',
        }).then((json) => {
            if (json.code === 0){
                this.setState({
                    name: json.data.name,
                    sign: json.data.sign,
                    des: json.data.description,
                })
            }
        })
    }
    fetchProjectMember = (props) => {
        API.query(baseURL + `/member/list?projectId=${props.project.id}`, {
            method: 'GET',
        }).then((json) => {
            if (json.code === 0){
                this.setState({
                    officials: json.data.masterMembers,
                    guests: json.data.slaveMembers,
                })
            }
        })
    }
    fetchInviteList = (props = this.props) => {
        API.query(baseURL + `/member/invite/list?projectId=${props.project.id}`, {
            method: 'GET',
        }).then((json) => {
            if (json.code === 0){
                this.setState({
                    invites: json.data,
                })
            }
        })
    }
    handleChangeDetail = () => {
        API.query( baseURL + `/project/${this.props.project.id}`, {
            method: 'PUT',
            body: JSON.stringify({
                description: this.state.des,
                name: this.state.name,
                sign: this.state.sign,
            })
        }).then((json) => {
            if (json.code === 0){
                message.success('修改成功',0.5)
                this.props.fresh()
            }
            else {
                message.error('修改失败，网络错误',0.5)
            }
        })
    }
    handleDelete = () => {
        API.query( baseURL + `/project/${this.props.project.id}`, {
            method: 'DELETE',
        }).then((json) => {
            if (json.code === 0){
                message.success('删除成功',0.5)
                this.props.fresh()
            }
            else {
                message.error('删除失败，网络错误',0.5)
            }
        })
    }
    renderDetail = () => {
        return <div className={styles.content}>
            <div className={styles.row}>
                <span className={styles.label}>项目名称</span>
                {
                    this.state.editingName ? <div className={styles.value}>
                        <Input autoFocus value={this.state.name} ref={(ref) => {this.nameInput = ref}} onChange={(e) => {this.setState({ name: e.target.value })}} onBlur={() => {this.setState({ editingName: false });this.handleChangeDetail()}}/>
                    </div> : <div className={styles.value}>
                        <span>{this.state.name}</span>
                        <Icon type="edit" onClick={() => { this.setState({ editingName: true })}}/>
                    </div>
                }
            </div>
            <div className={styles.row}>
                <span className={styles.label}>项目标识</span>
                {
                    this.state.editingSign ? <div className={styles.value}>
                        <Input autoFocus value={this.state.sign} ref={(ref) => {this.signInput = ref}} onChange={(e) => {this.setState({ sign: e.target.value })}} onBlur={() => {this.setState({ editingSign: false });this.handleChangeDetail()}}/>
                    </div> : <div className={styles.value}>
                        <span>{this.state.sign}</span>
                        <Icon type="edit" onClick={() => { this.setState({ editingSign: true })}}/>
                    </div>
                }
            </div>
            <div className={styles.row}>
                <span className={styles.label}>项目描述</span>
                {
                    this.state.editingDes ? <div className={styles.value}>
                        <TextArea autoFocus value={this.state.des} rows={4} ref={(ref) => { this.desInput = ref}} onChange={(e) => {this.setState({ des: e.target.value })}} onBlur={() => {this.setState({ editingDes: false });this.handleChangeDetail()}}/>
                    </div> : <div className={styles.value}>
                        <span>{this.state.des}</span>
                        <Icon type="edit" onClick={() => { this.setState({ editingDes: true })}}/>
                    </div>
                }

            </div>
        </div>
    }
    renderMembers = () => {
        return <div className={styles.content}>
            <div className={styles.row}>
                <span className={styles.label}>官方成员</span>
                <div className={styles.value}>
                    {
                        this.state.officials.map((v) => v.username).join(',')
                    }
                </div>
            </div>
            <div className={styles.row}>
                <span className={styles.label}>客方成员</span>
                <div className={styles.value}>
                    {
                        this.state.guests.map((v, k) => {
                            return <span key={k}>{v.username}</span>
                        })
                    }
                </div>
            </div>
        </div>
    }
    renderInvites = () => {
        return <div className={styles.content}>
            <div className={styles.row} style={{ paddingLeft: '10px' }}>
                {
                    this.state.invites.map((v) => `   ${v.username}(${v.email})`).join(',')
                }
            </div>
        </div>
    }
    render(){
        return <div className={styles.settingContainer}>
            <div className={styles.leftTab}>
                <div className={styles.title}>所有配置</div>
                <div className={styles.row} style={this.state.currentTab === 0 ? {backgroundColor: 'white', borderLeft: '6px solid #3798e9'} : null}>
                    <Icon type="project" />
                    <span>项目详情</span>
                </div>
            </div>
            <div className={styles.rightContent}>
                <div className={styles.title}>
                    项目信息
                </div>
                {
                    this.renderDetail()
                }
                <div className={styles.bottom}>
                    <div className={styles.delete} onClick={this.handleDelete}>
                        <Icon type="delete" />
                        <span>删除项目</span>
                    </div>
                </div>

                <div className={styles.title}>
                    项目成员
                </div>
                {
                    this.renderMembers()
                }


                <div className={styles.title}>
                    <span>邀请中成员</span>
                    <Button type="primary" onClick={() => {this.setState({ showInviteModal: true })}}>邀请新成员</Button>
                </div>
                {
                    this.renderInvites()
                }
            </div>
            {
                this.state.showInviteModal && <InviteModal cancel={() => { this.setState({ showInviteModal: false })}} projectId={this.props.project.id} cb={this.fetchInviteList}/>
            }
        </div>
    }
}

 export default SettingContainer