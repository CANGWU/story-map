import React from 'react'
import styles from './BoardContainer.scss'
import { Icon, Dropdown, message, Input, Modal } from 'antd'
import { baseURL, API } from '../config'
import { fromJS, toJS } from 'immutable'
import AddCardModal from './modal/AddCardModal'

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
        showCardBottom: -1,
        scrollWidth: 2000, //默认滚动长度
        renameEpic: -1,
        renameFeature: -1,
        isEditingGroup: -1,
        editingGroup: '未命名分组',
        showAddCard: -1, //控制新建卡片显示
        mouseInGroup: -1, //控制新建卡片显示
        showCardModal: false,
        belongFeatureId: -1,
        belongGroupId: -1,
        currentCard: null,
        cardCursor: -1, //新增卡片时的位置
        moveX: 0,
        moveY: 0,
        originX: 0,
        originY: 0,
        deltaX: 0,
        deltaY: 0,
        isPressed: false,
        moveItem: null,
        moveEpic: null,
        mouseInColumn: -2, //-1被占用
        moveType: '',
    }
    componentWillMount(){
        this.fetchDetail()
    }
    componentDidMount(){
        window.addEventListener('mousemove', this.handleMouseMove)
        window.addEventListener('mouseup', this.handleMouseUp)
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
                message.error('添加失败，网络错误',0.5)
                this.setState({
                    isAddingEpic: false,
                    editingEpic: '未命名epic',
                })
            }
        })
    }
    // 在右侧新建Epic
    handleAddEpic = (k, e) => {
        e.stopPropagation()
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
    handleDeleteEpic = (epic, e) => {
        e.stopPropagation()
        API.query(baseURL + `/epic/${epic.id}`, {
            method :'DELETE',
        }).then((json) => {
            if (json.code == 0){
                message.success('删除成功',0.5)
                this.fetchDetail()
            }
            else {
                message.error(json.data,0.5)
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
                message.error('修改失败,网络错误',0.5)
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
                message.error('添加失败，网络错误',0.5)
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
                message.success('删除成功',0.5)
                this.fetchDetail()
            }
            else {
                message.error(json.data,0.5)
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
                message.error('修改失败,网络错误',0.5)
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
        tmp.map((v, k) => {
            if (k == eIndex){
                v.featureList.splice(fIndex + 1, 0, {
                    id: -2,
                    name: '',
                    groupList: [],
                })
            }
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
                message.error(json.data,0.5)
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
                message.error(json.data,0.5)
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
                message.success('删除成功',0.5)
            }
            else {
                message.error(json.data,0.5)
            }
        })
    }
    handleDeleteCard = (card) => {
        API.query( baseURL + `/card/${card.id}`, {
            method: 'DELETE',
        }).then((json) => {
            if (json.code == 0){
                message.success('删除成功',0.5)
                this.fetchDetail()
            }
            else {
                message.error('删除失败,网络错误',0.5)
            }
        })
    }

    handleMouseMove = ({pageX, pageY}) => {
        let {
            isPressed,
            deltaX,
            deltaY,
        } = this.state
        if (isPressed){
            let x = pageX - this.scrollRef.offsetLeft - deltaX
            let y = pageY - this.scrollRef.offsetTop - deltaY
            this.setState({
                moveX: x,
                moveY: y,
            })
        }
    }
    handleMouseUp = () => {
        let { isPressed, moveItem, moveEpic, epicList, mouseInColumn, moveType } = this.state
        if (moveType === 'epic'){
            if (isPressed && moveItem.id !== mouseInColumn && mouseInColumn !== -2 ){
                let precursor = null
                epicList.map((v, k) => {
                    if (v.id === mouseInColumn){
                        if (k === 0){
                            precursor = null
                        }
                        else {
                            if (mouseInColumn > moveItem.id){
                                precursor = mouseInColumn
                            }
                            else {
                                precursor = epicList[k - 1].id
                            }
                        }
                    }
                })
                API.query(baseURL + `/map/epic/move/${moveItem.id}`, {
                    method: 'PUT',
                    body: JSON.stringify({
                        precursor: precursor,
                    })
                }).then((json) => {
                    this.fetchDetail()
                })
            }
        }
        if (moveType === 'feature'){
            if (isPressed && moveEpic.id !== mouseInColumn && mouseInColumn !== -2){
                let tmp = epicList.filter((v) => v.id == mouseInColumn)[0].featureList
                API.query(baseURL + `/map/feature/move/${moveItem.id}`, {
                    method: 'PUT',
                    body: JSON.stringify({
                        targetEpicId: mouseInColumn,
                        precursor: tmp.length == 0 ? null : tmp.length,
                    })
                }).then((json) => {
                    this.fetchDetail()
                })
            }
        }
        this.setState({
            moveX: this.state.originX,
            moveY: this.state.originY,
            originX: 0,
            originY: 0,
            deltaX: 0,
            deltaY: 0,
            isPressed: false,
            mouseInColumn: -2,
            moveItem: null,
            moveEpic: null,
            moveType: '',
        })
    }
    handleMouseDown = (moveItem, moveType, e) => {
        const deltaX = e.pageX - this.scrollRef.offsetLeft - e.currentTarget.offsetLeft
        const deltaY = e.pageY - this.scrollRef.offsetTop - e.currentTarget.offsetTop
        const moveX = e.currentTarget.offsetLeft
        const moveY = e.currentTarget.offsetTop
        this.setState({
            moveX, //移动中的鼠标
            moveY,
            deltaX, //鼠标与边框的相对位置
            deltaY,
            originX: e.currentTarget.offsetLeft,
            originY: e.currentTarget.offsetTop,
            isPressed: true,
            moveItem,
            moveType,
        })
    }
    handleMoveFeature = (moveEpic, moveItem, moveType, e) => {
        const deltaX = e.pageX - this.scrollRef.offsetLeft - e.currentTarget.offsetLeft
        const deltaY = e.pageY - this.scrollRef.offsetTop - e.currentTarget.offsetTop
        const moveX = e.currentTarget.offsetLeft
        const moveY = e.currentTarget.offsetTop
        this.setState({
            moveX, //移动中的鼠标
            moveY,
            deltaX, //鼠标与边框的相对位置
            deltaY,
            originX: e.currentTarget.offsetLeft,
            originY: e.currentTarget.offsetTop,
            isPressed: true,
            moveItem,
            moveEpic,
            moveType,
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
                                <div className={styles.column} key={k}
                                     onMouseEnter={() => {if (this.state.isPressed && this.state.moveItem.id !== t.id){ this.setState({ mouseInColumn: t.id })}}}
                                     onMouseLeave={() => {if (this.state.isPressed && this.state.moveItem.id !== t.id){this.setState({ mouseInColumn: -2 })}}}
                                     style={ this.state.mouseInColumn === t.id ? {opacity: 0.5, backgroundColor: '#4a4a4a'} : {}}>
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
                                        }} onMouseDown={this.handleMouseDown.bind(null, t, 'epic' )} style={{ opacity: (this.state.moveItem && t.id === this.state.moveItem.id) ? 0.5 : 1 }}
                                        >
                                            <span className={styles.name} title={t.name}>{t.name}</span>
                                            {
                                                this.state.showEpicBottom == t.id && <div className={styles.bottom}>
                                                    <Icon type="delete" title="删除"
                                                          onClick={this.handleDeleteEpic.bind(this, t)}/>
                                                    <Icon type="edit" title="重命名" onClick={(e) => {e.stopPropagation();this.setState({ renameEpic: t.id, editingEpic: t.name })}}/>
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
                                                                }}
                                                                     onMouseDown={this.handleMoveFeature.bind(null, t, f, 'feature' )}
                                                                >
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
                            return <div className={styles.groupContainer} key={k} onMouseEnter={() => {this.setState({ mouseInGroup: g.id })}} onMouseLeave={() => {this.setState({ mouseInGroup: -1 })}}>
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
                                                            return <div className={styles.featureColumn} key={fk} onMouseEnter={() => {
                                                                this.setState({
                                                                    showAddCard: f.id,
                                                                })
                                                            }} onMouseLeave={() => {
                                                                this.setState({
                                                                    showAddCard: -1,
                                                                })
                                                            }}>
                                                                {
                                                                    f.groupList.map((gr, gk) => {
                                                                        if (gr.id == g.id){
                                                                            return gr.cardList.map((c, ck) => {
                                                                                return <div className={styles.block} key={ck} onMouseEnter={() => { this.setState({ showCardBottom: c.id })}}
                                                                                            onMouseLeave={() => { this.setState({ showCardBottom: -1 })}}>
                                                                                    <span className={styles.name}
                                                                                          title={c.title}>{c.title}</span>
                                                                                    {
                                                                                        this.state.showCardBottom == c.id && <div className={styles.bottom}>
                                                                                            <Icon type="delete" title="删除" onClick={this.handleDeleteCard.bind(this, c)}/>
                                                                                            <Icon type="edit" title="编辑" onClick={() => {
                                                                                                this.setState({
                                                                                                    showCardModal: true,
                                                                                                    currentCard: c,
                                                                                                })
                                                                                            }}/>
                                                                                            <Icon type="down-circle" title="在下方新建卡片" onClick={() => {
                                                                                                this.setState({
                                                                                                    showCardModal: true,
                                                                                                    belongFeatureId: f.id,
                                                                                                    belongGroupId: g.id,
                                                                                                    currentCard: null,
                                                                                                    cardCursor: c.id,
                                                                                                })
                                                                                            }}/>
                                                                                        </div>
                                                                                    }
                                                                                </div>
                                                                            })
                                                                        }
                                                                    })
                                                                }
                                                                {
                                                                    this.state.showAddCard == f.id && this.state.mouseInGroup == g.id &&
                                                                    <div
                                                                        className={styles.addEpic}
                                                                        onClick={() => {this.setState({ showCardModal: true, belongFeatureId: f.id, belongGroupId: g.id, currentCard: null })}}
                                                                    >
                                                                        新建卡片
                                                                    </div>
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
                {
                    this.state.moveItem && <div className={styles.moveItem} style={{ left: this.state.moveX, top: this.state.moveY, boxShadow: 'rgba(0,0,0,0.2) 0px 16px 32px 0px', borderRadius: '3px' }}>
                        <span className={styles.name} >{this.state.moveItem.name}</span>
                    </div>
                }
            </div>
            {
                this.state.showCardModal && <AddCardModal
                    cancel={() => {this.setState({
                        showCardModal: false,
                        showAddCard: this.state.belongFeatureId,
                        mouseInGroup: this.state.belongGroupId,
                        belongFeatureId: -1,
                        belongGroupId: -1,
                        currentCard: null,
                        cardCursor: -1,
                    })}}
                    project={this.props.project} fresh={this.fetchDetail}
                    featureId={this.state.belongFeatureId}
                    groupId={this.state.belongGroupId}
                    currentCard={this.state.currentCard}
                    cardCursor={this.state.cardCursor}
                />
            }
        </div>
    }
}

export default BoardContainer