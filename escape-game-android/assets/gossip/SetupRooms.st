// tests with moving all the game to the Gossip side

EVAL Class addNewClass: (Object subclass: 'SetupRooms' variables: '' classVariables: '')
META SetupRooms
setup
	self initSetupRooms.
	self initRoom02.
	self initRoom03.
	self initRoom04.
!
META SetupRooms
initSetupRooms
    | box door01 |
     
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
    | hammer knife |

    hammer := (GameRegistry newObtainable: #hammer).
    hammer setImage: 'hammer.png'.

    knife := (GameRegistry newObtainable: #knife).
    knife setImage: 'knife.png'.
    knife setX: 530 Y: 5.
    knife addToRoomNamed: #room03.
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
	tree dropAtX: 480 Y: 20 item: #logs.
	tree setImage: 'tree.png'.
    tree setX: 480 Y: 20.
	tree addToRoomNamed: #room04.   
!
