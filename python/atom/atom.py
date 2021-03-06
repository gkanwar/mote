"""
Defines an atom within a mathematical expression.
"""

curIdent = 0
def getIdent():
    global curIdent
    curIdent += 1
    return curIdent

class Atom(object):
    ident = 0
    def __init__(self):
        self.ident = getIdent()
    
    """
    Render this atom to a Box, possibly containing other boxes as children.
    Should be overridden by subclasses.
    """
    def render(self):
        pass

    """
    Clone this atom, avoiding any duplicate references where this may cause
    an issue. Should be overridden by subclasses and implemented recursively.
    """
    def clone(self):
        assert False, "Must override clone in subclasses."

