package de.inmediasp.test.ordering;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

public class OrderedJRunner extends SpringJUnit4ClassRunner{

	public OrderedJRunner(Class<?> clazz) throws InitializationError {
		super(clazz);
	}

	@Override
	protected List<FrameworkMethod> computeTestMethods() {
		List<FrameworkMethod> methods= super.computeTestMethods();
		
		List<FrameworkMethod> copy = new ArrayList<FrameworkMethod>(methods);
        Collections.sort(copy, new Comparator<FrameworkMethod>() {
            @Override
            public int compare(FrameworkMethod f1, FrameworkMethod f2) {
                TestFirst tf1 = f1.getAnnotation(TestFirst.class);
                TestFirst tf2 = f2.getAnnotation(TestFirst.class);

                if(tf1 != null && tf2 != null) {
                	return f1.getName().compareTo(f2.getName());
                }

                if(tf1 != null) {
                	return -1;
                }
                if(tf2 != null) {
                	return 1;
                }
                
                TestLast tl1 = f1.getAnnotation(TestLast.class);
                TestLast tl2 = f2.getAnnotation(TestLast.class);
                
                if(tl1 != null && tl2 != null) {
                	return f1.getName().compareTo(f2.getName());
                }

                if(tl2 != null) {
                	return -1;
                }
                if(tl1 != null) {
                	return 1;
                }

                return f1.getName().compareTo(f2.getName());
             }
        });
		return copy;
	}

}