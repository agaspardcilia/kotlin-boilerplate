import {  Vue } from 'vue-property-decorator';
import { Problem } from '@/common/problem/Problem';
import { NotificationOptions } from 'vue-notification';

export const GlobalEventBus = new Vue();

const notify = (options: NotificationOptions) => {
  Vue.notify({
    duration: 5000,
    ...options
  });
};

const handleHttpError = (problem: Problem) => {
  notify({
    type: 'error',
    text: problem.details,
    title: problem.title
  });
};

const handleSuccessEvent = ({ title, text }: EventPayload) => {
  notify({ type: 'success', title, text });
};

const handleErrorEvent = ({ title, text }: EventPayload) => {
  notify({ type: 'error', title, text });
};


GlobalEventBus.$on('event', function (event: Event) {
  console.log('Event received!', event);

  switch (event.type) {

    case 'SUCCESS':
      handleSuccessEvent(event.payload);
      break;

    case 'ERROR':
      handleErrorEvent(event.payload);
      break;

    case 'HTTP_ERROR':
      handleHttpError(event.payload);
      break;

    default:
      console.warn('Unhandled event!', event);
      break;
  }
});

// TODO: try to put that in a proper class.
// @Component
// export class GlobalEventBus extends Vue {
//
//   @Emit()
//   onEvent(event: Event) {
//     console.log('Event received', event);
//
//     switch (event.type) {
//
//       case 'SUCCESS':
//         break;
//
//       case 'HTTP_ERROR':
//         this.handleHttpError(event.payload);
//         break;
//
//       default:
//         console.warn('Unhandled event!', event);
//         break;
//     }
//
//
//   }
//
//   private handleHttpError(problem: Problem) {
//     this.$notify({
//       text: problem.details,
//       title: problem.title,
//     });
//   }
//
//   private handleSuccessEvent({ title, text }: EventPayload) {
//     this.$notify({ type: 'success', title, text });
//   }
//
// }

export type Event = {
  type: 'HTTP_ERROR';
  payload: Problem;
} | {
  type: 'SUCCESS' | 'ERROR';
  payload: EventPayload;
};



export type EventPayload = {
  title: string;
  text?: string;
};



