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
createForClass: aClass
    | file mFile b methodFilename mm |

    file := File openWrite: folderName , '/' , aClass name , '.html'.
    file write:
        '<html><body><h1>Class ' , aClass name , '</h1><br><a href="Gossip.html">back</a><br>'.

    b := [ :meth :class | 
        methodFilename := meth name printString.
        mm := (meth name = '/')
            ifTrue: [ 'div' ]
            ifFalse: [ methodFilename ].
        methodFilename := aClass name , '_' ,  mm , '.html'.

        file write: '<a href="' , methodFilename , '">' , meth name printString , '</a><br>'.
        
        mFile := File openWrite: folderName, '/', methodFilename.
        mFile write:
            '<html><body><h1>Class ' , aClass name , '</h1><br>' ,
            '<a href="' , aClass name , '.html' , '">back</a><br>'.
        mFile write:
            '<pre>' , (self convertWhiteSpaces: meth text) , '</pre></html>' ].
        
    file write: '<h2>class methods</h2><br>'.    
    aClass class methods do: [ :m | b value: m value: aClass class ].
    
    file write: '<h2>instance methods</h2><br>'.    
    aClass methods do: [ :m | b value: m value: aClass ].
    
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
