import React from 'react'
import { Modal, Input, message } from 'antd'
import styles from './AddCardModal.scss'
import { baseURL, API } from '../../config'

class AddCardModal extends React.Component{
    state = {
        title: this.props.currentCard ? this.props.currentCard.title : '',
        des: this.props.currentCard ? this.props.currentCard.context : '',
    }
    handleOk = () => {
        if (this.state.title === '' || this.state.des === ''){
            message.error('请填写完整信息',0.5)
            return
        }
        else{
            if (this.props.currentCard){
                API.query(baseURL + `/card/${this.props.currentCard.id}`, {
                    method: 'PUT',
                    body: JSON.stringify({
                        title: this.state.title,
                        context: this.state.des,
                        type: 0,
                    })
                }).then((json) => {
                    if (json.code === 0){
                        message.success('修改成功',0.5)
                        this.props.fresh()
                        this.props.cancel()
                    }
                    else {
                        message.error('修改失败,网络错误',0.5)
                    }
                })
            }
            else {
                let data
                if (this.props.cardCursor !== -1){
                    data = {
                        title: this.state.title,
                        context: this.state.des,
                        type: 0,
                        belongFeatureId: this.props.featureId,
                        belongGroupId: this.props.groupId,
                        precursor: this.props.cardCursor
                    }
                }
                else {
                    data = {
                        title: this.state.title,
                        context: this.state.des,
                        type: 0,
                        belongFeatureId: this.props.featureId,
                        belongGroupId: this.props.groupId,
                    }
                }
                API.query(baseURL + `/card/create?projectId=${this.props.project.id}`, {
                    method: 'POST',
                    body: JSON.stringify(data)
                }).then((json) => {
                    if (json.code === 0){
                        message.success('添加成功',0.5)
                        this.props.fresh()
                        this.props.cancel()
                    }
                    else {
                        message.error('添加失败,网络错误',0.5)
                    }
                })
            }
        }
    }
    handleCancel = () => {
        this.props.cancel()
    }
    render(){
        return <Modal
            visible
            wrapClassName={styles.addCardModal}
            width={500}
            cancelText="取消"
            okText={this.props.currentCard ? '保存' : '添加'}
            title={this.props.currentCard ? '编辑卡片' : '添加卡片'}
            onOk={this.handleOk}
            onCancel={this.handleCancel}
            maskClosable={false}
        >
            <div className={styles.container}>
                <div className={styles.row}>
                    <span className={styles.label}>标题:</span>
                    <Input placeholder="请输入卡片标题..." value={this.state.title} onChange={(e) => { this.setState({ title: e.target.value })}}/>
                </div>
                <div className={styles.row}>
                    <span className={styles.label}>内容:</span>
                    <Input.TextArea rows={4} placeholder="请输入卡片内容..." value={this.state.des} onChange={(e) => {this.setState({ des: e.target.value })}}/>
                </div>
            </div>
        </Modal>
    }
}

export default AddCardModal