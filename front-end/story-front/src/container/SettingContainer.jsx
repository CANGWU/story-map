import React from 'react'
import styles from './SettingContainer.scss'
import { Icon, Input } from 'antd'

const { TextArea } = Input

class SettingContainer extends React.Component{
    state = {
        currentTab: 0,
        editingName: true,
        editingDes: false,
    }
    componentDidUpdate(){
        if (this.state.editingName){
            this.nameInput.focus()
        }
        if (this.state.editingDes){
            this.desInput.focus()
        }
    }
    render(){
        return <div className={styles.settingContainer}>
            <div className={styles.leftTab}>
                <div className={styles.title}>所有配置</div>
                <div className={styles.row} style={this.state.currentTab == 0 ? {backgroundColor: 'white', borderLeft: '6px solid #3798e9'} : null}>
                    <Icon type="project" />
                    <span>项目详情</span>
                </div>
            </div>
            <div className={styles.rightContent}>
                <div className={styles.title}>
                    项目信息
                </div>
                <div className={styles.content}>
                    <div className={styles.row}>
                        <span className={styles.label}>项目名称</span>
                        {
                            this.state.editingName ? <div className={styles.value}>
                                <Input placeholder="名称111" ref={(ref) => {this.nameInput = ref}} onBlur={() => {this.setState({ editingName: false })}}/>
                            </div> : <div className={styles.value}>
                                <span>名称111</span>
                                <Icon type="edit" onClick={() => { console.log('click name');this.setState({ editingName: true })}}/>
                            </div>
                        }
                    </div>
                    <div className={styles.row}>
                        <span className={styles.label}>项目描述</span>
                        {
                            this.state.editingDes ? <div className={styles.value}>
                                <TextArea rows={4} ref={(ref) => { this.desInput = ref}} onBlur={() => {this.setState({ editingDes: false })}}/>
                            </div> : <div className={styles.value}>
                                <span>描述111</span>
                                <Icon type="edit" onClick={() => { console.log('click des');this.setState({ editingDes: true })}}/>
                            </div>
                        }

                    </div>
                </div>
                <div className={styles.bottom}>
                    <div className={styles.delete}>
                        <Icon type="delete" />
                        <span>删除项目</span>
                    </div>
                </div>
            </div>
        </div>
    }
}

 export default SettingContainer