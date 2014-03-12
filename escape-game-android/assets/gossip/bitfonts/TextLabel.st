EVAL Class addNewClass: (JavaWrapper subclass: 'TextLabel' variables: 'name x y room visible' classVariables: '')




METHOD TextLabel
name: aString
    name := aString.
!
METHOD TextLabel
name
    ^ name.
!

METHOD TextLabel
register
	GameRegistry instance registerUITextLabel: self.
!

METHOD TextLabel
recreateJavaObject
    self javaObject: (GameRegistry newJavaUITextLabel: name).
   " self setImage: image."
    self setVisible: visible.
    
    room notNil
        ifTrue: [ self addToRoomNamed: room ].
    
    ((x notNil) and: [y notNil])
        ifTrue: [ self setX: x Y: y ].
!


METHOD TextLabel
addToRoomNamed: aString
    room := aString.
    self call: #addToRoomNamed with: aString.
!

METHOD TextLabel
setFontPath: aString
	self call: #setFontPath with: aString.
!


METHOD TextLabel
setText: aString
	self call: #setText with: aString.
!


METHOD TextLabel
setX: aNumber
    x := aNumber.
    self call: #setX with: x.
!
METHOD TextLabel
setY: aNumber
    y := aNumber.
    self call: #setY with: y.
!


