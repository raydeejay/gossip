// tests with moving all the game to the Gossip side

EVAL Class addNewClass: (Object subclass: 'Room01' variables: '' classVariables: '')
META Room01
setup
	self initRoom01.
	self initRoom02.
	self initRoom03.
	self initRoom04
!
META Room01
initRoom01
    | box |
     
    box := (GameRegistry newReactor: #box).
    box setImage: 'box.png'.
    box setX: 100 Y: 20.
    box onClick: [ box moveToInventory ].
    box addToRoomNamed: #room01
!
META Room01
initRoom02
    | key |
         
    key := (GameRegistry newReactor: #key).
    key setImage: 'key.png'.
    key setX: 200 Y: 20.
    key onClick: [ key moveToInventory ].
    key addToRoomNamed: #room02
!
META Room01
initRoom03
    | hammer knife |

    hammer := (GameRegistry newReactor: #hammer).
    hammer setImage: 'hammer.png'.
    hammer onClick: [ hammer moveToInventory ].

    knife := (GameRegistry newReactor: #knife).
    knife setImage: 'knife.png'.
    knife setX: 530 Y: 5.
    knife onClick: [ knife moveToInventory ].
    knife addToRoomNamed: #room03
!
META Room01
initRoom04
    | axe lighter logs |

    axe := (GameRegistry newReactor: #axe).
    axe setImage: 'axe.png'.
    axe setX: 100 Y: 40.
    axe onClick: [ axe moveToInventory ].
    axe addToRoomNamed: #room04.

    lighter := (GameRegistry newReactor: #lighter).
    lighter setImage: 'lighter.png'.
    lighter setX: 200 Y: 40.
    lighter onClick: [ lighter moveToInventory ].
    lighter addToRoomNamed: #room04.

    logs := (GameRegistry newReactor: #logs).
    logs setImage: 'logs.png'.
    logs onClick: [ logs moveToInventory ]
!
