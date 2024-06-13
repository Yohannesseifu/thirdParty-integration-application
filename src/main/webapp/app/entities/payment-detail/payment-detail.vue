<template>
  <div>
    <h2 id="page-heading" data-cy="PaymentDetailHeading">
      <span id="payment-detail-heading">Payment Details</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh list</span>
        </button>
        <router-link :to="{ name: 'PaymentDetailCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-payment-detail"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span>Create a new Payment Detail</span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && paymentDetails && paymentDetails.length === 0">
      <span>No Payment Details found</span>
    </div>
    <div class="table-responsive" v-if="paymentDetails && paymentDetails.length > 0">
      <table class="table table-striped" aria-describedby="paymentDetails">
        <thead>
          <tr>
            <th scope="row"><span>ID</span></th>
            <th scope="row"><span>Computed Payment Detail</span></th>
            <th scope="row"><span>Api Request</span></th>
            <th scope="row"><span>Operation</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="paymentDetail in paymentDetails" :key="paymentDetail.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PaymentDetailView', params: { paymentDetailId: paymentDetail.id } }">{{
                paymentDetail.id
              }}</router-link>
            </td>
            <td>{{ paymentDetail.computedPaymentDetail }}</td>
            <td>
              <div v-if="paymentDetail.apiRequest">
                <router-link :to="{ name: 'ApiRequestView', params: { apiRequestId: paymentDetail.apiRequest.id } }">{{
                  paymentDetail.apiRequest.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="paymentDetail.operation">
                <router-link :to="{ name: 'OperationView', params: { operationId: paymentDetail.operation.id } }">{{
                  paymentDetail.operation.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'PaymentDetailView', params: { paymentDetailId: paymentDetail.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'PaymentDetailEdit', params: { paymentDetailId: paymentDetail.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(paymentDetail)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <template #modal-title>
        <span id="thirdPartyIntegrationApplicationApp.paymentDetail.delete.question" data-cy="paymentDetailDeleteDialogHeading"
          >Confirm delete operation</span
        >
      </template>
      <div class="modal-body">
        <p id="jhi-delete-paymentDetail-heading">Are you sure you want to delete Payment Detail {{ removeId }}?</p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-paymentDetail"
            data-cy="entityConfirmDeleteButton"
            v-on:click="removePaymentDetail()"
          >
            Delete
          </button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./payment-detail.component.ts"></script>
