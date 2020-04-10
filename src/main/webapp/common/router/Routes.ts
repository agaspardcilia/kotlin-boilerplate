import { Authority } from '@/authentication/Authorities';
import { AuthenticationModule } from '@/authentication/AuthenticationModule';
import router, { ErrorPageName } from './index';
import { RouteConfig } from 'vue-router/types/router';

export type Route = RouteConfig & ({
  authenticationPolicy: Extract<AuthenticationPolicy, 'ignore'>;
} | {
  authenticationPolicy: Exclude<AuthenticationPolicy, 'ignore'>;
  authorities?: Authority[];
});

export type AuthenticationPolicy = 'authenticated' | 'notAuthenticated' | 'ignore';

export const redirectToHome = () => router.push('/');

export const makeRoute = (route: Route, authenticationModule: AuthenticationModule): RouteConfig => ({
  beforeEnter: (to, from, next) => { // TODO: looks way to complicated for such a simple thing.
    switch (route.authenticationPolicy) {
      case 'authenticated':
        if (authenticationModule.authenticationStatus !== 'authenticated') {
          next({ name: ErrorPageName });
        } else {

          if (route.authorities) {
            const userAuthorities = authenticationModule.currentUser!.authorities;
            if (!route.authorities.map(e => userAuthorities.includes(e)).some(e => e)) {
              next({ name: ErrorPageName });
            }
          }
          next();
        }
        break;

      case 'notAuthenticated':
        if (authenticationModule.authenticationStatus !== 'notAuthenticated') {
          next({ name: ErrorPageName });
        } else {
          next();
        }
        break;

      case 'ignore':
      default:
        next();
        break;
    }
  },
  ...route
});
