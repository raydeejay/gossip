EVAL Class addNewClass: (Object subclass: 'Gossipdoc' variables: 'folderName' classVariables: '')

COMMENT Gossipdoc
I am an HTML documentation generator.

I can create a set of HTML files in a directory, one for each
class and method in the system, in a nice format and interlinked
for viewing in a web browser.

The default directory is 'doc/' and it must exist.

I depend on HTMLMarkup and MarkdownMarkup.
!

METHOD Gossipdoc
initialize
    super initialize.
    folderName := 'doc'
!

METHOD Gossipdoc
convertWhiteSpaces: string
    | ans |
    
    ans := ''.
    string do: [ :c | 
        (c = Char newline)
            ifTrue: [ ans := ans , '<br>' , c asString ]
            ifFalse: [
                (c = (Char new: 9))
                    ifTrue: [ ans := ans , '&#09;' ]
                    ifFalse: [ ans := ans , c asString] ] ].
    ^ ans
!

METHOD Gossipdoc
fileNameForClass: aClass
	^ fileName := aClass name , '.html'
!

METHOD Gossipdoc
fileNameForMethod: aMethod inClass: aClass
    | methodName fileName |

	methodName := (aMethod name = '/')
	    ifTrue: [ 'div' ]
	    ifFalse: [ aMethod name printString ].

	fileName := aClass name , '__'.
    methodName do: [ :c |
        fileName := fileName , ((c = $:)
            ifTrue: [ '_' ]
            ifFalse: [ c asString ] ]).
    fileName := fileName , '.html'.

    ^ fileName
!

METHOD Gossipdoc
createForMethod: aMethod inClass: aClass
    | m file |

    m := HTMLMarkup new.

    m heading: 'Class ' , aClass name level: 1.
    m newline.
    m link: '[back]' to: aClass name , '.html'.
    m newline.

    m code: (self convertWhiteSpaces: method text).

    file := File openWrite: folderName, '/', (self fileNameForMethod: method inClass: class).
    file write: m render
!

METHOD Gossipdoc
createForClass: aClass
    | m writeMethodBlock file |

    m := HTMLMarkup new.

    writeMethodBlock := [ :method :class |
        m link: method name printString to: (self fileNameForMethod: method inClass: class).
        m newline.
        self createForMethod: method inClass: class ].

    m heading: 'Class ' , aClass name level: 1.
    m newline.
    m link: '[back]' to: 'index.html'
    m newline.

    m heading: 'class methods' level: 2.
    m newline.
    aClass class methods do: [ :each | writeMethodBlock value: each value: aClass class ].

    m heading: 'instance methods' level: 2.
    m newline.
    aClass methods do: [ :each | writeMethodBlock value: each value: aClass ].

    file := File openWrite: folderName , '/' , (self fileNameForClass: aClass).
    file write: m render.
!

METHOD Gossipdoc
createInFolderNamed: aFolderName
    "Write the whole system's documentation in HTML format"
    | m indexFile |

    folderName := aFolderName.
    m := HTMLMarkup new.

    m heading: 'The Gossip Smalltalk System' level: 1.
    Class classes do: [ :each |
        m link: each name to: each name, '.html'.
        m newline ].

    indexFile := File openWrite: folderName , '/' , 'index.html'.
    indexFile write: m render.

    Class classes do: [ :each | self createForClass: each ]
!
