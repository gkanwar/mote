import draw
from box import *
from symbol import Symbol
from util import Pos
from atom import Atom

"""
A sequence of terms to multiply. Not assumed commutative.
"""
class ProdAtom(Atom):
    terms = None
    def __init__(self, terms):
        super(ProdAtom, self).__init__()
        # DEBUG assertions
        assert isinstance(terms, list)
        assert len(terms) > 1
        self.terms = terms

    """
    Override Atom.render().
    """
    def render(self):
        box_elts = map(lambda t: t.render(), self.terms)
        box_elts.insert(0, Symbol("("))
        box_elts.append(Symbol(")"))
        return buildHorizBox(box_elts)

    def clone(self):
        return ProdAtom(map(lambda t: t.clone(), self.terms))

    def __str__(self):
        return "(" + " ".join(map(str, self.terms)) + ")"
