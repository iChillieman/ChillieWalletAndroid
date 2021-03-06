package com.chillieman.chilliewallet.di.annotation

import javax.inject.Scope

/**
 * In Dagger, an un-scoped component cannot depend on a scoped component. As the application
 * [dagger.Component] is a scoped component (`@Singleton`, we create a custom
 * scope to be used by all fragment components. Additionally, a component with a specific scope
 * cannot have a sub component with the same scope.
 */
@MustBeDocumented
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class FragmentScoped
