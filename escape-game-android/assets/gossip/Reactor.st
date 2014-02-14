EVAL Class addNewClass: (JavaWrapper subclass: 'Reactor' variables: 'name room states currentState image' classVariables: '')
METHOD Reactor
initialize
    super initialize.
"    javaObject := (System instantiateJavaClass: 'net.raydeejay.escapegame.Reactor')."
    states := Dictionary new.
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
room
    ^ room.
!
METHOD Reactor
setX: x Y: y
    self call: #at with: x with: y.
!
METHOD Reactor
setX: x
    self call: #setX with: x.
!
METHOD Reactor
setY: y
    self call: #setY with: y.
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
	self call: #removeFromRoom.
!
METHOD Reactor
addState: aState
    states at: aState name put: aState.
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
