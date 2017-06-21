#!/usr/bin/env python

import pytest
from jenkins_ci_demo.jenkins_ci_demo import DemoAdd


class TestDemoAdd(object):
    @pytest.mark.parametrize("x, y, expected", [
        (3, 4, 7),
        (4, 5, 9)
    ])
    def test_add(self, x, y, expected):
        assert DemoAdd.add(x, y) == expected
