// tests with moving all the game to the Gossip side

EVAL Class addNewClass: (Object subclass: 'SetupRooms' variables: '' classVariables: '')
META SetupRooms
setup
	self initRoom01A.
	self initRoom01B.
	self initRoom02.
	self initRoom03.
	self initRoom04.
!
META SetupRooms
initRoom01A
    | box door01 paper default |
     
    box := (GameRegistry newObtainable: #box).
    box setImage: 'box.png'.
    box setX: 100 Y: 20.
    box addToRoomNamed: #room01.
    
    door01 := (GameRegistry newDoor: #door01).
    door01 destination: #room02.
    door01 setX: 270 Y: 82.
    door01 imageForOpen: 'door2open.png'.
    door01 imageForClosed: 'door2.png'.
    door01 close.
    door01 addToRoomNamed: #room01.

    paper := (GameRegistry newReactor: #paper).
    paper setImage: 'paper.png'.
    default := State new name: #default.
    default onClick: [ GameRegistry instance winGame ].
    paper addState: default.
    paper switchToState: #default.
!
META SetupRooms
initRoom01B
	| vase state1 state2 state3 |
    vase := (GameRegistry newDropper: #vase).
    vase setX: 200 Y: 30.
    vase setImage: 'vase.png'.
    vase reactTo: #hammerItem.
    vase dropAtX: 600 Y: 30 item: #paper type: Reactor.
    
    state1 := State new name: #state1.
    state1 onClick: [ vase switchToState: #state2 ].

    state2 := State new name: #state2.
    state2 onEnter: [ vase setX: 400 ].
    state2 onClick: [ vase switchToState: #state3 ].

    state3 := State new name: #state3.
    state3 onEnter: [ vase setX: 600. vase switchToState: #default ].

    vase addState: state1.
    vase addState: state2.
    vase addState: state3.
    vase switchToState: #state1.
    vase addToRoomNamed: #room01.
!
META SetupRooms
initRoom02
    | key door02 |
         
    key := (GameRegistry newObtainable: #key).
    key setImage: 'key.png'.
    key setX: 200 Y: 20.
    key addToRoomNamed: #room02.

    door02 := (GameRegistry newDoor: #door02).
    door02 destination: #room01.
    door02 setX: 270 Y: 82.
    door02 imageForOpen: 'door2open.png'.
    door02 imageForClosed: 'door2.png'.
    door02 imageForLocked: 'door2.png'.
    door02 key: #keyItem.
    door02 lock.
    door02 addToRoomNamed: #room02.
!
META SetupRooms
initRoom03
    | hammer knife fireplace unlit lit |

    hammer := (GameRegistry newObtainable: #hammer).
    hammer setImage: 'hammer.png'.

    knife := (GameRegistry newObtainable: #knife).
    knife setImage: 'knife.png'.
    knife setX: 530 Y: 5.
    knife addToRoomNamed: #room03.
    
    lit := GameRegistry newDropper: #litFire.
	lit reactTo: #boxItem.
	lit dropAtX: 325 Y: 85 item: #hammer type: Obtainable.
	lit setImage: 'fire.png'.

    unlit := GameRegistry newDropper: #unlitFire.
	unlit reactTo: #lighterItem.
	unlit dropAtX: 325 Y: 85 item: #litFire type: Dropper.
	unlit setImage: 'logs.png'.
    
    fireplace := GameRegistry newDropper: #fireplace.
	fireplace reactTo: #logsItem.
	fireplace dropAtX: 325 Y: 85 item: #unlitFire type: Dropper.
	fireplace setImage: 'fireplace.png'.
    fireplace setX: 200 Y: 82.
    fireplace shouldStay.
	fireplace addToRoomNamed: #room03.   
    
!
META SetupRooms
initRoom04
    | axe lighter logs tree |

    axe := (GameRegistry newObtainable: #axe).
    axe setImage: 'axe.png'.
    axe setX: 100 Y: 40.
    axe addToRoomNamed: #room04.

    lighter := (GameRegistry newObtainable: #lighter).
    lighter setImage: 'lighter.png'.
    lighter setX: 200 Y: 40.
    lighter addToRoomNamed: #room04.

    logs := (GameRegistry newObtainable: #logs).
    logs setImage: 'logs.png'.
    
    tree := GameRegistry newDropper: #tree.
	tree reactTo: #axeItem.
	tree dropAtX: 480 Y: 20 item: #logs type: Obtainable.
	tree setImage: 'tree.png'.
    tree setX: 480 Y: 20.
	tree addToRoomNamed: #room04.   
!