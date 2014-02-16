////////////////////////////////////////////////////////////
// Gossipdoc generator. Produced results is a list of .html files
// You need to have a folder Gossipdoc 
// http://bergel.eu/Gossip/scripts/
// http://bergel.eu/Gossip/Gossipdoc/Gossip.html
//
// Author: Alexandre Bergel (alexandre@bergel.eu)
////////////////////////////////////////////////////////////
EVAL Class addNewClass: ( Object subclass: 'Gossipdoc' variables: 'folderName' classVariables: '')
METHOD Gossipdoc
convertWhiteSpaces: string
	| ans |
	ans := ''.
	string do: [:c| 
		(c = Char newline)
			ifTrue: [ans := ans, '<br>', c asString]
			ifFalse: [(c = (Char new: 9))
						ifTrue: [ans := ans, '&#09;']
						ifFalse: [ans := ans, c asString]]].
	^ ans
!
METHOD Gossipdoc
createForClass: aClass
	| file mFile b methodFilename mm |
	file := File openWrite: folderName, '/', aClass name, '.html'.
	file write: '<html><body><h1>Class ', aClass name, '</h1><br><a href="Gossip.html">back</a><br>'.
	b := [:meth :class | 
			methodFilename := meth name printString.
			mm := (meth name = '/') ifTrue: ['div'] ifFalse: [methodFilename].
			methodFilename := aClass name, '_',  mm , '.html'. 
			file write: '<a href="', methodFilename , '">', meth name printString, '</a><br>'.
			mFile := File openWrite: folderName, '/', methodFilename.
			mFile write: '<html><body><h1>Class ', aClass name, '</h1><br><a href="', aClass name, '.html' ,'">back</a><br><pre>'.
			mFile write: (self convertWhiteSpaces: meth text).
			mFile write: '</pre></html>'].
	file write: '<h2>instance methods</h2><br>'.	
	aClass methods do: [:m| b value: m value: aClass].
	file write: '<h2>class methods</h2><br>'.	
	aClass class methods do: [:m| b value: m value: aClass class].
	file write: '</body></html>'.
!
METHOD Gossipdoc
createInFolderNamed: aFolderName
	| mFile |
	folderName := aFolderName.
	mFile := File openWrite: folderName, '/', 'Gossip.html'.
	mFile write: '<html>'.
	mFile write: '<body>'.
	mFile write: '<h1>The Gossip Smalltalk System</h1>'.
	Class classes do: [:each| 
		self createForClass: each.
		mFile write: '<a href="', each name, '.html">', each name, '</a><br>'].
	mFile write: '</body>'.
	mFile write: '</html>'
!
METHOD Gossipdoc
initialize
	super initialize.
	folderName := 'doc'
!
