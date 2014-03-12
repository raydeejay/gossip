// Demo game using the escape-game framework

// Load the framework
EVAL File fileInWithNotification: 'escape/Item.st' 
EVAL File fileInWithNotification: 'escape/Obtainable.st'
EVAL File fileInWithNotification: 'escape/Dropper.st'
EVAL File fileInWithNotification: 'escape/Door.st'

// Create the game classes
EVAL Class addNewClass: (Object subclass: 'DemoGame' variables: '' classVariables: '')
META DemoGame
start
    GameRegistry instance switchToRoom: #room02
!
META DemoGame
setup
    self setupNavigation.
    self createRooms.
	self initRoom01A.
	self initRoom01B.
	self initRoom02.
	self initRoom03.
	self initRoom04.
!
META DemoGame
setupNavigation
    | left right |
    
    left := (GameRegistry newHudElement: #arrowLeft) onClick: [ 
        GameRegistry instance switchToRoom: GameRegistry instance currentRoom left ].
    left setImage: 'demo/sprites/arrowLeft.png'.
    left setX: 10 Y: 240.
    right := (GameRegistry newHudElement: #arrowRight) onClick: [ 
        GameRegistry instance switchToRoom: GameRegistry instance currentRoom right ].
    right setImage: 'demo/sprites/arrowRight.png'.
    right setX: 620 Y: 240.
!
META DemoGame
createRooms
    | r1 r2 r3 r4 |

    r1 := GameRegistry newRoom: #room01.
    r1 background: 'demo/backgrounds/room01.png'.
    
    r2 := GameRegistry newRoom: #room02.
    r2 background: 'demo/backgrounds/room02.png'.
    r2 right: #room03.
    
    r3 := GameRegistry newRoom: #room03.
    r3 background: 'demo/backgrounds/room03.png'.
    r3 left: #room02.
    r3 right: #room04.
    
    r4 := GameRegistry newRoom: #room04.
    r4 background: 'demo/backgrounds/room04.png'.
    r4 left: #room03.
!
META DemoGame
initRoom01A
    | box door01 paper default |
     
    box := (GameRegistry newObtainable: #box).
    box setImage: 'demo/sprites/box.png'.
    box setX: 100 Y: 20.
    box addToRoomNamed: #room01.
    
    door01 := (GameRegistry newDoor: #door01).
    door01 destination: #room02.
    door01 setX: 270 Y: 82.
    door01 imageForOpen: 'demo/sprites/door2open.png'.
    door01 imageForClosed: 'demo/sprites/door2.png'.
    door01 close.
    door01 addToRoomNamed: #room01.

    paper := (GameRegistry newReactor: #paper).
    paper setImage: 'demo/sprites/paper.png'.
    default := State new name: #default.
    default onClick: [ GameRegistry instance winGame ].
    paper addState: default.
    paper switchToState: #default.
!
META DemoGame
initRoom01B
	| vase state1 state2 state3 |
    vase := (GameRegistry newDropper: #vase).
    vase setX: 200 Y: 30.
    vase setImage: 'demo/sprites/vase.png'.
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
META DemoGame
initRoom02
    | key door02 itaros|
         
    key := (GameRegistry newObtainable: #key).
    key setImage: 'demo/sprites/key.png'.
    key setX: 200 Y: 20.
    key addToRoomNamed: #room02.

    door02 := (GameRegistry newDoor: #door02).
    door02 destination: #room01.
    door02 setX: 270 Y: 82.
    door02 imageForOpen: 'demo/sprites/door2open.png'.
    door02 imageForClosed: 'demo/sprites/door2.png'.
    door02 imageForLocked: 'demo/sprites/door2.png'.
    door02 key: #keyItem.
    door02 lock.
    door02 addToRoomNamed: #room02.
    
    
    itaros := GameRegistry newUITextLabel: 'god'.
    itaros addToRoomNamed: #room02.
    itaros setFontPath: 'fonts/Klaudia_colored'.
    itaros setX: 10.
    itaros setY: 400.
    itaros setText: 'Itaros was here'.
    ErrorHandlerHelper check: itaros.

!
META DemoGame
initRoom03
    | hammer knife fireplace unlit lit fpX fpY |

    hammer := (GameRegistry newObtainable: #hammer).
    hammer setImage: 'demo/sprites/hammer.png'.

    knife := (GameRegistry newObtainable: #knife).
    knife setImage: 'demo/sprites/knife.png'.
    knife setX: 530 Y: 5.
    knife addToRoomNamed: #room03.
    
    "terrible hack because there is no support for Z-ordering yet"
    "plus it doesn't work because of flawed reasoning :D"
    fpX := 200.
    fpY := 82.
    
    lit := GameRegistry newDropper: #litFire.
	lit reactTo: #boxItem.
	lit dropAtX: fpX + 125 Y: fpY + 3 item: #hammer type: Obtainable.
	lit setImage: 'demo/sprites/fire.png'.

    unlit := GameRegistry newDropper: #unlitFire.
	unlit reactTo: #lighterItem.
	unlit dropAtX: fpX + 125 Y: fpY + 3 item: #litFire type: Dropper.
	unlit setImage: 'demo/sprites/logs.png'.
    
    fireplace := GameRegistry newDropper: #fireplace.
	fireplace reactTo: #logsItem.
	fireplace dropAtX: fpX + 125 Y: fpY + 3 item: #unlitFire type: Dropper.
	fireplace setImage: 'demo/sprites/fireplace.png'.
    fireplace setX: fpX Y: fpY.
    fireplace shouldStay.
	fireplace addToRoomNamed: #room03.   
    
!
META DemoGame
initRoom04
    | axe lighter logs tree |

    axe := (GameRegistry newObtainable: #axe).
    axe setImage: 'demo/sprites/axe.png'.
    axe setX: 100 Y: 40.
    axe addToRoomNamed: #room04.

    lighter := (GameRegistry newObtainable: #lighter).
    lighter setImage: 'demo/sprites/lighter.png'.
    lighter setX: 200 Y: 40.
    lighter addToRoomNamed: #room04.

    logs := (GameRegistry newObtainable: #logs).
    logs setImage: 'demo/sprites/logs.png'.
    
    tree := GameRegistry newDropper: #tree.
	tree reactTo: #axeItem.
	tree dropAtX: 480 Y: 20 item: #logs type: Obtainable.
	tree setImage: 'demo/sprites/tree.png'.
    tree setX: 480 Y: 20.
	tree addToRoomNamed: #room04.   
!
