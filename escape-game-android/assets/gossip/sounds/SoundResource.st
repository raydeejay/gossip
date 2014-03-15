EVAL Class addNewClass: (JavaWrapper subclass: 'SoundResource' variables: 'name' classVariables: '')


METHOD SoundResource
name: aString
    name := aString.
!
METHOD SoundResource
name
    ^ name.
!

METHOD SoundResource
register
	GameRegistry instance registerSoundResource: self.
!

METHOD SoundResource
recreateJavaObject
    self javaObject: (GameRegistry newJavaSoundResource: name).
!


METHOD SoundResource
setPath: aString
	self call: #setPath with: aString.
!

METHOD SoundResource
load
	self call: #tryLoad.
!