import pygame
import draw
from atom import *
from util import Pos

if __name__ == "__main__":
    x = VarAtom("x")
    y = VarAtom("y")
    stmt = UnaryOpAtom("\\integral", BinOpAtom("+", x, y))
    
    b = stmt.render()
    draw.boxes = [b]

    # Event loop
    running = True
    while running:
        draw.draw()
        running = draw.events()
