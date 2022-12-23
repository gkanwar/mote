import draw
from box import *
from symbol import Symbol
from util import Pos
from atom import Atom

"""
A unary operation atom.
"""
class UnaryOpAtom(Atom):
    op = None
    r = None
    def __init__(self, op, r):
        super(UnaryOpAtom, self).__init__()
        # DEBUG assertions
        assert isinstance(op, basestring)
        assert isinstance(r, Atom)
        
        self.op = op
        self.r = r

    """
    Override Atom.render().
    """
    def render(self):
        sym = Symbol(self.op)
        return buildHorizBox([sym, self.r.render()])

    def clone(self):
        return UnaryOpAtom(self.op, self.r.clone())
                   
    def __str__(self):
        return "(" + self.op + str(self.r) + ")"
