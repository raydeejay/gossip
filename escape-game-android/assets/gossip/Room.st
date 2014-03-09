EVAL Class addNewClass: (JavaWrapper subclass: 'Room' variables: 'name left right up down background' classVariables: '')
METHOD Room
initialize
    super initialize.
!
METHOD Room
background: aString
    background := aString.
    self call: #setBackgroundFilename with: aString.
!
METHOD Room
background
    ^ background
!
METHOD Room
name: aString
    name := aString.
!
METHOD Room
name
    ^ name.
!
METHOD Room
register
	GameRegistry instance registerRoom: self.
!
METHOD Room
left: aString
    left := aString.
!
METHOD Room
left
    ^ left
!
METHOD Room
right: aString
    right := aString.
!
METHOD Room
right
    ^ right
!
METHOD Room
up: aString
    up := aString.
!
METHOD Room
up
    ^ up
!
METHOD Room
down: aString
    down := aString.
!
METHOD Room
down
    ^ down
!
