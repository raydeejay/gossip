EVAL Class addNewClass: ( Object subclass: 'GameBrowser' variables: 'window roomsPane reactorsPane inventoryPane executeButton actionButton2 sourcePane' classVariables: '')
METHOD GameBrowser
compile: t intoClass: aClassIndex meta: aBoolean
    | c |

    aBoolean
        ifTrue: [ c <- (Class classes at: aClassIndex) class ]
        ifFalse: [ c <- (Class classes at: aClassIndex) ].

    (c compileMethod: t) ifTrue: [
	    Window notify: 'compilation successful' ]

!
METHOD GameBrowser
layout
    ^ '(COLUMN 
        (ROW weight=0.45 rooms reactors inventory)
        (ROW weight=0.10 execute action2)
        source)
    '.
!
METHOD GameBrowser
defaultRoomsPane
    | p rooms |

    rooms := GameRegistry instance rooms.
    p := Pane list: rooms allKeys asStringArray action: [ :i |
        self reactorsListFor: i list: reactorsPane. ].
    ^ p

!
METHOD GameBrowser
defaultReactorsPane
    ^ Pane list: #() action: [:i | 
        sourcePane setText: '' ].
!
METHOD GameBrowser
defaultInventoryPane
    ^ Pane
        list: [ 
            (((GameRegistry instance reactors allValues) 
                select: [ :r | r room isNil ])
                    collect: [ :x | x name ]) asArray ] value
        action: [ :i | sourcePane setText: '' ].
!
METHOD GameBrowser
reactorsListFor: aRoomIndex list: aPane
    | roomName reactorList |
    
    roomName := GameRegistry instance rooms allKeys at: aRoomIndex.
    reactorList := (GameRegistry instance reactors allValues) select: [ :r | r room = roomName ].  
        
    aPane setList: 
        (reactorList collect: [ :x | x name ]) asArray ].
!
METHOD GameBrowser
open
    | pane roomName reactor reactorList |

    pane := MultiSplitPane newWithLayout: self layout.

    roomsPane := self defaultRoomsPane.
    pane add: roomsPane at: 'rooms'.

    reactorsPane := self defaultReactorsPane.
    pane add: reactorsPane at: 'reactors'.

    inventoryPane := self defaultInventoryPane.
    pane add: inventoryPane at: 'inventory'.

    executeButton := Pane title: 'execute' action: [
    	"execute the code inside the source pane in the context of the selected reactor"
    	
    	roomName := GameRegistry instance rooms allKeys at: (roomsPane getIndex). 
        reactorList := (GameRegistry instance reactors allValues) select: [ :r | r room = roomName ].  
    	reactor := reactorList at: (reactorsPane getIndex).
    	
    	reactor class compileMethod: ('doIt ' + sourcePane getText).
    	reactor doIt.
    	
    	"the compilation may fail and this message below may not be sent, this should be fixed"
    	reactor class removeMethod: #doIt ].
    pane add: executeButton at: 'execute'.

    actionButton2 := Pane title: 'refresh inventory' action: [
        reactorList := (GameRegistry instance reactors allValues) select: [ :r | r room isNil ].
        inventoryPane setList: (reactorList collect: [ :x | x name ]) asArray ].
    pane add: actionButton2 at: 'action2'.

    sourcePane := self defaultSourcePane.
    pane add: sourcePane at: 'source'.

    window := Window new title: 'Game Browser'.
    window width: 400 height: 500.
    window setPane: pane javaObject.
    window show

!
METHOD GameBrowser
resetRoomsPane
    roomsPane setList: GameRegistry instance rooms allKeys asStringArray 
!
META GameBrowser
open
    ^ self new open
!
METHOD GameBrowser
defaultSourcePane
    | t |

    t <- Pane textArea.
    t setText: '
















'.
    ^ t

!
