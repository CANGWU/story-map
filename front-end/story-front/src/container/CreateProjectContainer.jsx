import React from 'react'
import styles from './CreateProjectContainer.scss'
import { Input, Button } from 'antd'

const { TextArea } = Input
class CreateProjectContainer extends React.Component{
    render(){
        return <div className={styles.createProjectContainer}>
            <div className={styles.content}>
                <div className={styles.title}>新建项目</div>
                <hr/>
                <div className={styles.form}>
                    <div className={styles.row}>
                        <div className={styles.label}>项目名称:</div>
                        <div className={styles.value}>
                            <Input/>
                        </div>
                    </div>
                    <div className={styles.row} style={{ alignItems: 'flex-start' }}>
                        <div className={styles.label} style={{ position: 'relative', top: '3px' }}>项目描述:</div>
                        <div className={styles.value}>
                            <TextArea rows={4} />
                        </div>
                    </div>
                    <div className={styles.bottom}>
                        <Button type="primary">确定</Button>
                        <Button onClick={() => {this.props.history.push('/index')}}>取消</Button>
                    </div>
                </div>
            </div>

        </div>
    }
}

export default CreateProjectContainer