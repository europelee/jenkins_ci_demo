#!/usr/bin/env python


class DemoAdd:
    @staticmethod
    def add(x, y):
        return x + y


def main():
    print "demo"
    print "trigger"
    print "hi"
    demo = DemoAdd()
    print demo.add(3, 4)


if __name__ == "__main__":
    main()
