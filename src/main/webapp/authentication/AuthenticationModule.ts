import { User } from '@/authentication/User';
import { http } from '@/common/http/Http';
import { SessionStorage } from '@/common/SessionStorage';
import { JWT_SESSION_KEY } from '@/common/Constants';
import { Action, Module, Mutation, VuexModule } from 'vuex-module-decorators'
import Store from '@/common/store'
import { Event, GlobalEventBus } from '@/common/GlobalEventBus';

const AUTHENTICATION_ENDPOINT = '/authenticate';
const CURRENT_USER_ENDPOINT = '/users/current-user';


@Module({ dynamic: true, store: Store, name: 'authentication' })
export class AuthenticationModule extends VuexModule {

  authenticationStatus: AuthenticationStatus = 'notAuthenticated';

  currentUser: User | null = null;

  @Action
  async authenticate(requestParameters: AuthenticationRequestParameters) {
    try {
      this.setStatus('pending');

      const { data } = await http.doPost<AuthenticationRequestParameters, AuthenticationSuccess>(AUTHENTICATION_ENDPOINT, requestParameters);
      SessionStorage.put(JWT_SESSION_KEY, data.id_token);
      http.addJwtIfPresent(data.id_token);

      const getCurrentUserResponse = await http.doGet<{}, User>(CURRENT_USER_ENDPOINT);

      this.setStatus('authenticated');
      this.setCurrentUser(getCurrentUserResponse.data);

      GlobalEventBus.$emit('event', {
        type: 'SUCCESS',
        payload: { title: 'Authentication success', text: 'You are now connected.' }
      } as Event);

    } catch (e) {
      this.setStatus('failure');
      console.error('Authentication failure!');
      console.error(e);
    }
  }

  @Mutation
  setStatus(status: AuthenticationStatus) {
    this.authenticationStatus = status;
  }

  @Mutation
  setCurrentUser(user: User | null) {
    this.currentUser = user;
  }
}


export type AuthenticationStatus = 'notAuthenticated' | 'pending' | 'authenticated' | 'failure';

export type AuthenticationRequestParameters = {
  username: string;
  password: string;
  rememberMe: boolean;
};

export type AuthenticationSuccess = {
  id_token: string;
}

