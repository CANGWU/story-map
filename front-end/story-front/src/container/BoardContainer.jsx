import React from 'react'
import styles from './BoardContainer.scss'
import { Icon, Dropdown } from 'antd'

const test = [{
    name: 'column1',
    features: [
        {
            name: 'a',
            groups: [{
                content: 'aaa',
            }, {
                content: 'aab',
            }]
        }, {
            name: 'bb',
            groups: [{
                content: 'bbb',
            }, {
                content: 'bbc',
            }]
        }
    ],
}, {
    features: [],
}, {
    features: [],
}]
const groups = ['group1', 'group2', 'group3', 'group4']


class BoardContainer extends React.Component{
    render(){
        let groupOpt = (<div className={styles.optContainer}>
            <div className={styles.optRow}>编辑名称</div>
            <div className={styles.optRow}>删除分组</div>
            <div className={styles.optRow}>在下方新建分组</div>
        </div>)

        return <div className={styles.boardContainer}>
            {/*<div className={styles.toolBar}></div>*/}
            <div className={styles.content}>
                <div className={styles.columnHeader}>
                    {
                        test.map((t, k) => {
                            return <div className={styles.column} key={k}>
                                <div className={styles.titleBlock}>
                                    {t.name}
                                </div>
                                <div className={styles.featureList}>
                                    {
                                        t.features.map((f, fk) => {
                                            return <div className={styles.featureBlock} key={fk}>
                                                {f.name}
                                            </div>
                                        })
                                    }
                                </div>
                            </div>
                        })
                    }
                </div>
                <div className={styles.columnContent} ref={(ref) => {this.scrollRef = ref}}>
                    {
                        groups.map((g, k) => {
                            return <div className={styles.groupContainer}>
                                <div className={styles.titleContainer}>
                                    <Icon type="plus" />
                                    <span className={styles.title}>{g}</span>
                                    <Dropdown overlayClassName={styles.groupOpt} overlay={groupOpt} trigger="click" getPopupContainer={() => {return this.scrollRef }}>
                                        <Icon type="caret-down" />
                                    </Dropdown>
                                </div>
                                <div className={styles.groupContent}>
                                    {
                                        test.map((t, tk) => {
                                            return <div className={styles.column} key={tk}>
                                                    {
                                                        t.features.map((f, fk) => {
                                                            return <div className={styles.featureColumn} key={fk}>
                                                                {
                                                                    f.groups.map((g, gk) => {
                                                                        return <div className={styles.block} key={gk}>
                                                                            {g.content}
                                                                        </div>
                                                                    })
                                                                }
                                                            </div>
                                                        })
                                                    }
                                            </div>
                                        })
                                    }
                                </div>
                            </div>
                        })
                    }
                </div>

            </div>
        </div>
    }
}

export default BoardContainer