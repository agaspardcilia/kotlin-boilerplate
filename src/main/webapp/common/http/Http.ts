import Vue from 'vue-property-decorator';
import Axios, { AxiosInstance, AxiosResponse } from 'axios';
import { SessionStorage } from '@/common/SessionStorage';
import { BASE_API_URL, JWT_SESSION_KEY } from '@/common/Constants';
import { Problem } from '@/common/problem/Problem';
import { Event, GlobalEventBus } from '@/common/GlobalEventBus';


class Http {

  private axiosInstance: AxiosInstance;

  constructor() {
    this.axiosInstance = Axios.create({
      baseURL: BASE_API_URL
    });

    this.axiosInstance.interceptors.response.use(undefined, err => { // TODO externalize that function (not in the constructor anyway...)
      console.log('Axios error!');
      if (err && err.response) {
        const { response } = err;
        let problem: Problem;
        console.log('Status', response.status, typeof response.status);

        switch (response.status) {
          case 401:
          case 403:
            problem = {
              ...response.data
            };
            break;

          case 500:
            problem = {
              title: 'Server internal error',
              details: 'Something went wrong...',
              type: ''
            };
            break;

          default:
            problem = { // TODO: put a default error format.
              title: '',
              details: '',
              type: ''
            };
            break;

        }
        this.handleProblem(response.status, problem);

        return Promise.reject(problem);
      } else {
        this.handleError();
        return Promise.reject(err);
      }
    });

    this.addJwtIfPresent(SessionStorage.get(JWT_SESSION_KEY));
  }

  addJwtIfPresent(jwt: string | null) {
    if (jwt) {
      this.axiosInstance.defaults.headers.Authorization = `Bearer ${jwt}`;
    }
  }

  async doGet<I extends GetRequestParameters, O>(path: string, params?: I): Promise<AxiosResponse<O>> {

    return await this.axiosInstance.get<I, AxiosResponse<O>>(path + (params ? this.encodeGetParameters(params) : ''));
  }

  async doPost<I, O>(path: string, params: I): Promise<AxiosResponse<O>> {
    return await this.axiosInstance.post<I, AxiosResponse<O>>(path, params).then();
  }

  async doPut<I, O>(path: string, params: I): Promise<AxiosResponse<O>> {
    return await this.axiosInstance.put<I, AxiosResponse<O>>(path, params);
  }

  async doDelete<I, O>(path: string, params: I): Promise<AxiosResponse<O>> {
    return await this.axiosInstance.delete<I, AxiosResponse<O>>(path, params);
  }

  private encodeGetParameters<T extends GetRequestParameters>(params: GetRequestParameters): string {
    if (!params) {
      return '';
    }

    const formattedParameters = Object.keys(params)
      .map(k => k + '=' + params[k])
      .join('&');

    return '?' + formattedParameters;
  }

  private handleProblem(status: number, problem: Problem) {
    GlobalEventBus.$emit('event', { type: 'HTTP_ERROR', payload: problem } as Event);
  }

  private handleError() {
    GlobalEventBus.$emit('event', {
      type: 'ERROR',
      payload: { title: 'Network error', text: 'The server is unreachable, check your internet connection or try again later.' }
    } as Event)
  }
}

export type GetRequestParameters = {
  [k in string]: string | boolean | number;
};

export const http = new Http();
