"""
Defines an atom within a mathematical expression.
"""

class Atom:
    """
    Render this atom to a Box, possibly containing other boxes as children.
    Should be overridden by subclasses.
    """
    def render(self):
        pass

from varatom import *
from binopatom import *
