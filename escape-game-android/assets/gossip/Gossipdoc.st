EVAL Class addNewClass: (Object subclass: 'Gossipdoc' variables: 'folderName generatorClass' classVariables: '')

COMMENT Gossipdoc
I am the Gossip documentation generator.

By default I create a set of HTML files in a directory, one for each
class and method in the system, in a nice format and interlinked
for viewing in a web browser.

I can also create a set of Github-flavoured Markdown files.

I can use any other Markup subclass as a generator.

The default directory is 'doc/' and it must exist.

I depend on HTMLMarkup.
!

META Gossipdoc
new: aMarkupGenerator
    ^ self new generatorClass: aMarkupGenerator
!

METHOD Gossipdoc
initialize
    super initialize.
    generatorClass := HTMLMarkup.
    folderName := 'doc'
!

METHOD Gossipdoc
generatorClass: aClass
    generatorClass := aClass
!

METHOD Gossipdoc
generatorClass
    ^ generatorClass
!

METHOD Gossipdoc
convertWhiteSpaces: string
    | ans |

    self generatorClass = HTMLMarkup
        ifFalse: [ ^ string ].

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
    ^ aClass name , generatorClass fileExtension
!

METHOD Gossipdoc
fileNameForMethod: aMethod inClass: aClass
    | methodName fileName |

    methodName := (aMethod name = '/')
        ifTrue: [ 'div' ]
        ifFalse: [ aMethod name printString ].

    fileName := aClass name , '__'.
    methodName do: [ :c |
        fileName := fileName , ( c = $:
            ifTrue: [ '_' ]
            ifFalse: [ c asString ] ) ].
    fileName := fileName , generatorClass fileExtension.

    ^ fileName
!

METHOD Gossipdoc
linkForClass: aClass
    ^ aClass name , generatorClass linkExtension
!

METHOD Gossipdoc
linkForMethod: aMethod inClass: aClass
    | methodName fileName |

    methodName := (aMethod name = '/')
        ifTrue: [ 'div' ]
        ifFalse: [ aMethod name printString ].

    fileName := aClass name , '__'.
    methodName do: [ :c |
        fileName := fileName , ( c = $:
            ifTrue: [ '_' ]
            ifFalse: [ c asString ] ) ].
    fileName := fileName , generatorClass linkExtension.

    ^ fileName
!
METHOD Gossipdoc
createForMethod: aMethod inClass: aClass
    | m file |

    m := generatorClass new.

    m heading: 'Class ' , aClass name level: 1.
    m newline.
    m link: '[back]' to: (self linkForClass: aClass).
    m newline.

    m code: (self convertWhiteSpaces: aMethod text).

    file := File openWrite: folderName, '/', (self fileNameForMethod: aMethod inClass: aClass).
    file write: m render
!

METHOD Gossipdoc
writeVariablesForClass: aClass using: aGenerator
    aGenerator heading: 'class variables' level: 2.
    aGenerator newline.
    aClass class notNil ifTrue: [
        "apparently this can be nil too..."
        aClass class variables notNil ifTrue: [
            aClass class variables do: [ :v | aGenerator italic: v , '' ] ] ].
    aGenerator newline.

    aGenerator heading: 'instance variables' level: 2.
    aGenerator newline.
    aClass variables notNil ifTrue: [
        aClass variables do: [ :v | aGenerator italic: v , '' ] ].
    aGenerator newline
!

METHOD Gossipdoc
createForClass: aClass
    | m writeMethodBlock file |

    m := generatorClass new.

    writeMethodBlock := [ :method :class |
        m link: method name printString to: (self linkForMethod: method inClass: class).
        m newline ].

    m heading: 'Class ' , aClass name level: 1.
    m newline.
    m link: '[back]' to: 'index' , generatorClass linkExtension.
    m newline.

    m heading: 'comment' level: 2.
    m blockQuote: (self convertWhiteSpaces: (Documentation for: aClass)).
    m newline.

    self writeVariablesForClass: aClass using: m.

    m heading: 'class methods' level: 2.
    m newline.
    aClass class notNil ifTrue: [
        aClass class methods do: [ :method |
            writeMethodBlock value: method value: aClass class.
            self createForMethod: method inClass: aClass class ] ].

    m heading: 'instance methods' level: 2.
    m newline.
    aClass methods do: [ :method |
        writeMethodBlock value: method value: aClass.
        self createForMethod: method inClass: aClass ].

    file := File openWrite: folderName , '/' , (self fileNameForClass: aClass).
    file write: m render.
!

METHOD Gossipdoc
createInFolderNamed: aFolderName
    "Write the whole system's documentation in HTML format"
    | m indexFile |

    folderName := aFolderName.
    m := generatorClass new.

    m heading: 'The Gossip Smalltalk System' level: 1.
    Class classes do: [ :each |
        m link: each name to: (self linkForClass: each).
        m newline ].

    indexFile := File openWrite: folderName , '/' , 'index' , generatorClass fileExtension.
    indexFile write: m render.

    Class classes do: [ :each | self createForClass: each ]
!