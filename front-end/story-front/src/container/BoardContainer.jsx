import React from 'react'
import styles from './BoardContainer.scss'
import { Icon, Dropdown, message, Input } from 'antd'
import { baseURL, API } from '../config'
import { fromJS, toJS } from 'immutable'


class BoardContainer extends React.Component{
    state = {
        canRender: false,
        epicList: [],
        groupList: [],
        isAddingEpic: false,
        editingEpic: '未命名epic',
        isAddingFeature: -1,
        editingFeature: '未命名feature',
        showEpicBottom: -1,
        showFeatureBottom: -1,
        scrollWidth: 2000, //默认滚动长度
        renameEpic: -1,
        renameFeature: -1,
        isEditingGroup: -1,
        editingGroup: '未命名分组',
    }
    componentWillMount(){
        this.fetchDetail()
    }
    componentDidUpdate(){
        //动态更新block底部border的长度
        if (this.scrollRef.scrollWidth !== this.state.scrollWidth){
            this.setState({
                scrollWidth: this.scrollRef.scrollWidth,
            })
        }
    }
    fetchDetail = () => {
        API.query( baseURL + `/map/details?projectId=${this.props.project.id}`, {
            method: 'GET',
        }).then((json) => {
            this.setState({
                epicList: json.epicList,
                groupList: json.groupList,
                canRender: true,
            })
        })
    }
    handleCreateEpic = (index) => {
        let name = this.state.editingEpic == '' ? '未命名epic' : this.state.editingEpic
        let data
        if (index) {
            data = {
                name: name,
                precursor: this.state.epicList[index - 1].id,
            }
        }
        else {
            data = {
                name: name,
            }
        }
        API.query(baseURL + `/epic/create?projectId=${this.props.project.id}`, {
            method: 'POST',
            body: JSON.stringify(data)
        }).then((json) => {
            if (json.code == 0){
                API.query( baseURL + `/map/details?projectId=${this.props.project.id}`, {
                    method: 'GET',
                }).then((res) => {
                    this.setState({
                        epicList: res.epicList,
                        groupList: res.groupList,
                        isAddingEpic: false,
                        editingEpic: '未命名epic',
                    })
                })
            }
            else {
                message.error('添加失败，网络错误')
                this.setState({
                    isAddingEpic: false,
                    editingEpic: '未命名epic',
                })
            }
        })
    }
    // 在右侧新建Epic
    handleAddEpic = (k) => {
        let tmp = fromJS(this.state.epicList).toJS()
        tmp.splice(k + 1, 0, {
            featureList:[],
            id: -1,
            name: '',
        })
        this.setState({
            epicList: tmp
        })
    }
    handleDeleteEpic = (epic) => {
        API.query(baseURL + `/epic/${epic.id}`, {
            method :'DELETE',
        }).then((json) => {
            if (json.code == 0){
                message.success('删除成功')
                this.fetchDetail()
            }
            else {
                message.error(json.data)
            }
        })
    }
    handleRenameEpic = (epicId) => {
        let name = this.state.editingEpic == "" ? '未命名epic' : this.state.editingEpic
        API.query(baseURL + `/epic/${epicId}`, {
            method: 'PUT',
            body: JSON.stringify({
                name: name
            })
        }).then((json) => {
            if (json.code == 0){
                API.query( baseURL + `/map/details?projectId=${this.props.project.id}`, {
                    method: 'GET',
                }).then((res) => {
                    this.setState({
                        epicList: res.epicList,
                        groupList: res.groupList,
                        editingEpic: '未命名epic',
                        renameEpic: -1,
                    })
                })
            }
            else {
                message.error('修改失败,网络错误')
                this.setState({
                    editingEpic: '未命名epic',
                    renameEpic: -1,
                })
            }
        })
    }
    handleCreateFeature = (epidId, featureId) => {
        let name = this.state.editingFeature == "" ? '未命名feature' : this.state.editingFeature
        let data
        if (featureId){
            data = {
                name: name,
                precursor: featureId,
            }
        }
        else {
            data = {
                name: name,
            }
        }
        API.query(baseURL + `/feature/create?epicId=${epidId}`, {
            method: 'POST',
            body: JSON.stringify(data)
        }).then((json) => {
            if (json.code == 0){
                API.query( baseURL + `/map/details?projectId=${this.props.project.id}`, {
                    method: 'GET',
                }).then((res) => {
                    this.setState({
                        epicList: res.epicList,
                        groupList: res.groupList,
                        isAddingFeature: -1,
                        editingFeature: '未命名feature',
                    })
                })
            }
            else {
                message.error('添加失败，网络错误')
                this.setState({
                    isAddingFeature: -1,
                    editingFeature: '未命名feature',
                })
            }
        })
    }
    handleDeleteFeature = (feature) => {
        API.query(baseURL + `/feature/${feature.id}`, {
            method: 'DELETE',
        }).then((json) => {
            if (json.code == 0){
                message.success('删除成功')
                this.fetchDetail()
            }
            else {
                message.error(json.data)
            }
        })
    }
    handleRenameFeature = (featureId) => {
        let name = this.state.editingFeature == "" ? '未命名feature' : this.state.editingFeature
        API.query(baseURL + `/feature/${featureId}`, {
            method: 'PUT',
            body: JSON.stringify({
                name: name
            })
        }).then((json) => {
            if (json.code == 0){
                API.query( baseURL + `/map/details?projectId=${this.props.project.id}`, {
                    method: 'GET',
                }).then((res) => {
                    this.setState({
                        epicList: res.epicList,
                        groupList: res.groupList,
                        editingFeature: '未命名feature',
                        renameFeature: -1,
                    })
                })
            }
            else {
                message.error('修改失败,网络错误')
                this.setState({
                    editingFeature: '未命名epic',
                    renameFeature: -1,
                })
            }
        })
    }

    //在右侧新建Feature
    handleAddFeature = (eIndex, fIndex) => {
        let tmp = fromJS(this.state.epicList).toJS()
        let res = []
        tmp.map((v, k) => {
            if (k == eIndex){
                v.featureList.splice(fIndex + 1, 0, {
                    id: -2,
                    name: '',
                    groupList: [],
                })
            }
            res.push(v)
        })
        this.setState({
            epicList: tmp,
        })
    }
    //在下方新建分组
    handleAddGroup = (index) => {
        let tmp = fromJS(this.state.groupList).toJS()
        tmp.splice(index + 1, 0, {
            id: -2,
            name: '',
        })
        this.setState({
            groupList: tmp,
        })
    }
    handleRenameGroup = (group) => {
        let name = this.state.editingGroup == '' ? '未命名分组' : this.state.editingGroup
        API.query(baseURL + `/group/${group.id}`, {
            method: 'PUT',
            body: JSON.stringify({
                name: name,
            })
        }).then((json) => {
            if (json.code == 0){
                API.query( baseURL + `/map/details?projectId=${this.props.project.id}`, {
                    method: 'GET',
                }).then((res) => {
                    this.setState({
                        epicList: res.epicList,
                        groupList: res.groupList,
                        isEditingGroup: -1,
                        editingGroup: '未命名分组',
                    })
                })
            }
            else {
                message.error(json.data)
                this.setState({
                    isEditingGroup: -1,
                    editingGroup: '未命名分组',
                })
            }
        })
    }
    handleCreateGroup = (precursor) => {
        API.query(baseURL + `/group/create?projectId=${this.props.project.id}`, {
            method: 'POST',
            body: JSON.stringify({
                name: this.state.editingGroup,
                precursor: precursor,
            })
        }).then((json) => {
            if (json.code == 0){
                API.query( baseURL + `/map/details?projectId=${this.props.project.id}`, {
                    method: 'GET',
                }).then((res) => {
                    this.setState({
                        epicList: res.epicList,
                        groupList: res.groupList,
                        editingGroup: '未命名分组',
                    })
                })
            }
            else {
                message.error(json.data)
                this.setState({
                    editingGroup: '未命名分组',
                })
            }
        })
    }
    handleDeleteGroup = (group) => {
        API.query(baseURL + `/group/${group.id}`, {
            method: 'DELETE',
        }).then((json) => {
            if (json.code == 0){
                this.fetchDetail()
                message.success('删除成功')
            }
            else {
                message.error(json.data)
            }
        })
    }
    render(){
        let { epicList, groupList } = this.state
        let groupOpt = (group, key) => (<div className={styles.optContainer}>
            <div className={styles.optRow} onClick={() => {this.setState({ isEditingGroup: group.id, editingGroup: group.name })}}>编辑名称</div>
            {
                groupList.length > 1 && <div className={styles.optRow} onClick={this.handleDeleteGroup.bind(this, group)}>删除分组</div>
            }
            <div className={styles.optRow} onClick={this.handleAddGroup.bind(this, key)}>在下方新建分组</div>
        </div>)
        if (!this.state.canRender){
            return null
        }
        return <div className={styles.boardContainer}>
            {/*<div className={styles.toolBar}></div>*/}
            <div className={styles.content} ref={(ref) => {this.scrollRef = ref}}>
                <div className={styles.columnHeader}>
                    {
                        epicList.length == 0 ? <div className={styles.column}>
                                {
                                    this.state.isAddingEpic ? <div className={styles.titleBlock} style={{ cursor: 'text' }}>
                                        <textarea
                                            autoFocus
                                            onChange={(e) => {this.setState({ editingEpic: e.target.value })}} value={this.state.editingEpic}
                                            onFocus={(e) => {e.target.select()}}
                                            onBlur={this.handleCreateEpic.bind(this, null)}
                                        />
                                    </div> : <div className={styles.addEpic} onClick={() => {this.setState({ isAddingEpic: true })}}>
                                        新建标签
                                    </div>
                                }

                            </div> :
                        epicList.map((t, k) => {
                            return t.id == -1 ? <div className={styles.column} key={k}>
                                    <div className={styles.titleBlock} style={{ cursor: 'text' }}>
                                        <textarea
                                            autoFocus
                                            onChange={(e) => {this.setState({ editingEpic: e.target.value })}} value={this.state.editingEpic}
                                            onFocus={(e) => {e.target.select()}}
                                            onBlur={this.handleCreateEpic.bind(this, k)}
                                        />
                                    </div>
                                </div> :
                                <div className={styles.column} key={k}>
                                    {
                                        this.state.renameEpic == t.id ? <div className={styles.titleBlock}>
                                             <textarea
                                                 autoFocus
                                                 onChange={(e) => {this.setState({ editingEpic: e.target.value })}} value={this.state.editingEpic}
                                                 onFocus={(e) => {e.target.select()}}
                                                 onBlur={this.handleRenameEpic.bind(this, t.id)}
                                             />
                                        </div> : <div className={styles.titleBlock} onMouseEnter={() => {
                                            this.setState({showEpicBottom: t.id})
                                        }} onMouseLeave={() => {
                                            this.setState({showEpicBottom: -1})
                                        }}>
                                            <span className={styles.name} title={t.name}>{t.name}</span>
                                            {
                                                this.state.showEpicBottom == t.id && <div className={styles.bottom}>
                                                    <Icon type="delete" title="删除"
                                                          onClick={this.handleDeleteEpic.bind(this, t)}/>
                                                    <Icon type="edit" title="重命名" onClick={() => {this.setState({ renameEpic: t.id, editingEpic: t.name })}}/>
                                                    <Icon type="right-circle" title="在右侧新建"
                                                          onClick={this.handleAddEpic.bind(this, k)}/>
                                                </div>
                                            }
                                        </div>
                                    }

                                    {
                                        t.featureList.length == 0 ?
                                            (
                                                <div className={styles.featureList}>
                                                    {
                                                        this.state.isAddingFeature == t.id ?
                                                            <div className={styles.featureBlock}>
                                                                <textarea
                                                                    autoFocus
                                                                    onChange={(e) => {
                                                                        this.setState({editingFeature: e.target.value})
                                                                    }}
                                                                    onFocus={(e) => {
                                                                        e.target.select()
                                                                    }}
                                                                    value={this.state.editingFeature}
                                                                    onBlur={this.handleCreateFeature.bind(this, t.id, null)}
                                                                >
                                                                </textarea>
                                                            </div>
                                                            :
                                                            <div className={styles.addEpic} onClick={() => {
                                                                this.setState({isAddingFeature: t.id})
                                                            }}>新建标签
                                                            </div>
                                                    }
                                                </div>
                                            )
                                            :
                                            <div className={styles.featureList}>
                                                {
                                                    t.featureList.map((f, fk) => {
                                                        return this.state.renameFeature == f.id ?
                                                            <div className={styles.featureBlock}>
                                                                <textarea
                                                                    autoFocus
                                                                    onChange={(e) => {
                                                                        this.setState({editingFeature: e.target.value})
                                                                    }}
                                                                    onFocus={(e) => {
                                                                        e.target.select()
                                                                    }}
                                                                    value={this.state.editingFeature}
                                                                    onBlur={this.handleRenameFeature.bind(this, f.id)}
                                                                >
                                                                </textarea>
                                                            </div> :
                                                            f.id == -2 ?
                                                                <div className={styles.featureBlock}>
                                                                    <textarea
                                                                        autoFocus
                                                                        onChange={(e) => {
                                                                            this.setState({editingFeature: e.target.value})
                                                                        }}
                                                                        onFocus={(e) => {
                                                                            e.target.select()
                                                                        }}
                                                                        value={this.state.editingFeature}
                                                                        onBlur={this.handleCreateFeature.bind(this, t.id, t.featureList[fk - 1].id)}
                                                                    />
                                                                </div> :
                                                                <div className={styles.featureBlock} key={fk}
                                                                     onMouseEnter={() => {
                                                                         this.setState({showFeatureBottom: f.id})
                                                                     }} onMouseLeave={() => {
                                                                    this.setState({showFeatureBottom: -1})
                                                                }}>
                                                                <span className={styles.name}
                                                                      title={f.name}>{f.name}</span>
                                                                    {
                                                                        this.state.showFeatureBottom == f.id &&
                                                                        <div className={styles.bottom}>
                                                                            <Icon type="delete" title="删除"
                                                                                  onClick={this.handleDeleteFeature.bind(this, f)}/>
                                                                            <Icon type="edit" title="重命名"
                                                                                  onClick={() => {
                                                                                      this.setState({
                                                                                          renameFeature: f.id,
                                                                                          editingFeature: f.name
                                                                                      })
                                                                                  }}/>
                                                                            <Icon type="right-circle" title="在右侧新建"
                                                                                  onClick={this.handleAddFeature.bind(this, k, fk)}/>
                                                                        </div>
                                                                    }
                                                                </div>
                                                    })
                                                }
                                            </div>
                                    }
                                </div>
                        })
                    }
                    <div className={styles.border} style={{  width: `${this.state.scrollWidth}px` }}/>
                </div>
                <div className={styles.columnContent} ref={(ref) => {this.scrollRef = ref}}>
                    {
                        groupList.map((g, k) => {
                            return <div className={styles.groupContainer} key={k}>
                                {
                                    g.id == -2 ? <div className={styles.titleContainer}>
                                            <Icon type="plus"/>
                                            <Input
                                                autoFocus
                                                value={this.state.editingGroup}
                                                onChange={(e) => {
                                                    this.setState({editingGroup: e.target.value})
                                                }}
                                                onBlur={this.handleCreateGroup.bind(this, groupList[k - 1].id)}
                                                onFocus={(e) => {
                                                    e.target.select()
                                                }}
                                                onKeyDown={(e) => {
                                                    if (e.keyCode == 13) {
                                                        this.handleCreateGroup(groupList[k - 1].id)
                                                    }
                                                }}
                                            />
                                        </div>
                                        :
                                        (this.state.isEditingGroup == g.id ? <div className={styles.titleContainer}>
                                            <Icon type="plus"/>
                                            <Input
                                                autoFocus
                                                value={this.state.editingGroup}
                                                onChange={(e) => {
                                                    this.setState({editingGroup: e.target.value})
                                                }}
                                                onBlur={this.handleRenameGroup.bind(this, g)}
                                                onFocus={(e) => {
                                                    e.target.select()
                                                }}
                                                onKeyDown={(e) => {
                                                    if (e.keyCode == 13) {
                                                        this.handleRenameGroup(g)
                                                    }
                                                }}
                                            />
                                        </div> : <div className={styles.titleContainer}>
                                            <Icon type="plus"/>
                                            <span className={styles.title}>{g.name}</span>
                                            <Dropdown overlayClassName={styles.groupOpt} overlay={groupOpt(g, k)}
                                                      trigger="click" getPopupContainer={() => {
                                                return this.scrollRef
                                            }}>
                                                <Icon type="caret-down"/>
                                            </Dropdown>
                                        </div>)
                                }
                                <div className={styles.groupContent}>
                                    {
                                        epicList.map((t, tk) => {
                                            return <div className={styles.column} key={tk}>
                                                    {
                                                        t.featureList.map((f, fk) => {
                                                            return <div className={styles.featureColumn} key={fk}>
                                                                {
                                                                    f.groupList.map((gr, gk) => {
                                                                        return <div className={styles.block} key={gk}>
                                                                            {gr.content}
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
                                <div className={styles.border} style={{ width: `${this.state.scrollWidth}px` }}/>
                            </div>
                        })
                    }
                </div>

            </div>
        </div>
    }
}

export default BoardContainer