import draw
from box import *
from symbol import Symbol
from util import Pos
from atom import Atom

"""
A variable atom.
"""
class VarAtom(Atom):
    name = None
    def __init__(self, name):
        self.name = name

    """
    Override Atom.render().
    """
    def render(self):
        sym = Symbol(self.name)
        return buildHorizBox([sym])
