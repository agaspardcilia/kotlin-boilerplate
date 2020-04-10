import Vue from 'vue';
import VueRouter from 'vue-router';
import AuthenticationComponent from '@/authentication/AuthenticationComponent.vue';
import HomeComponent from '@/home/Home.vue';
import { RouteConfig } from 'vue-router/types/router';
import { AuthenticationModule } from '@/authentication/AuthenticationModule';
import { getModule } from 'vuex-module-decorators';
import { makeRoute } from '@/common/router/Routes';
import ErrorComponent from '@/common/problem/ErrorComponent.vue';

Vue.use(VueRouter);

export const ErrorPageName = 'ErrorPage';

const authenticationModule: AuthenticationModule = getModule(AuthenticationModule);

const routes: RouteConfig[] = [
  makeRoute({
    path: '/',
    name: 'Home',
    component: HomeComponent,
    authenticationPolicy: 'ignore'
  }, authenticationModule),
  makeRoute({
    path: '/auth',
    name: 'Authenticate',
    component: AuthenticationComponent,
    authenticationPolicy: 'notAuthenticated'
  }, authenticationModule),
  {
    path: '/error',
    name: ErrorPageName,
    component: ErrorComponent
  }
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
});

export default router;
