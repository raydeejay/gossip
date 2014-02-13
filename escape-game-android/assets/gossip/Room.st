EVAL Class addNewClass: (JavaWrapper subclass: 'Room' variables: 'name' classVariables: '')
METHOD Room
initialize
    super initialize.
!
METHOD Room
background: aString
    self call: #setBackgroundFilename with: aString.
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
    self call: #setExitLeft with: aString.
!
METHOD Room
right: aString
    self call: #setExitRight with: aString.
!
METHOD Room
top: aString
    self call: #setExitTop with: aString.
!
METHOD Room
bottom: aString
    self call: #setExitBottom with: aString.
!
