EVAL Class addNewClass: (JavaWrapper subclass: 'Reactor' variables: 'name x y room states currentState image visible' classVariables: '')
METHOD Reactor
initialize
    super initialize.
"    javaObject := (System instantiateJavaClass: 'net.raydeejay.escapegame.Reactor')."
    states := Dictionary new.
    room := nil.
!
METHOD Reactor
javaObject: aJavaObject
	javaObject := aJavaObject.
    self installClickListener.
!
METHOD Reactor
javaObjectKeepListener: aJavaObject
	javaObject := aJavaObject.
!
METHOD Reactor
installClickListener
    <150 javaObject [ currentState clicked. ]>.
!
METHOD Reactor
setImage: aString
	image := aString.
    self call: #setImage with: aString.
!
METHOD Reactor
getImage
    ^ image.
!
METHOD Reactor
addToRoomNamed: aString
    room := aString.
    self call: #addToRoomNamed with: aString.
!
METHOD Reactor
name: aString
    name := aString.
!
METHOD Reactor
name
    ^ name.
!
METHOD Reactor
room: aString
    room := aString
!
METHOD Reactor
room
    ^ room.
!
METHOD Reactor
setX: aX Y: aY
    self setX: aX.
    self setY: aY.
!
METHOD Reactor
setX: aNumber
    x := aNumber.
    self call: #setX with: x.
!
METHOD Reactor
setY: aNumber
    y := aNumber.
    self call: #setY with: y.
!
METHOD Reactor
setVisible: aBoolean
    "booleans can't be sent to Java?"
    aBoolean
        ifTrue: [ self beVisible ]
        ifFalse: [ self beInvisible ].
!
METHOD Reactor
beVisible
    visible := true.
    self call: #beVisible
!
METHOD Reactor
beInvisible
    visible := false.
    self call: #beInvisible
!
METHOD Reactor
visible
    ^ visible
!
METHOD Reactor
x
    ^ x
!
METHOD Reactor
y
    ^ y
!
METHOD Reactor
moveToInventory
    | anItem |
    anItem := GameRegistry newItemFrom: self.
    GameRegistry instance addToInventory: anItem.
	self removeFromRoom.
!
METHOD Reactor
removeFromRoom
    room := nil.
	self call: #removeFromRoom.
!
METHOD Reactor
addState: aState
    states at: aState name put: aState.
!
METHOD Reactor
getState: aString
    ^ states at: aString ifAbsent: [ nil ]
!
METHOD Reactor
switchToState: aStateName
    currentState notNil ifTrue: [ currentState exit. ].
    currentState := (states at: aStateName).
    currentState enter.
!
METHOD Reactor
register
	GameRegistry instance registerReactor: self.
!
METHOD Reactor
clicked
    currentState clicked
!
METHOD Reactor
recreateJavaObject
    self javaObject: (GameRegistry newJavaReactor: name).
    self setImage: image.
    self setVisible: visible.
    
    room notNil
        ifTrue: [ self addToRoomNamed: room ].
    
    ((x notNil) and: [y notNil])
        ifTrue: [ self setX: x Y: y ].
!
