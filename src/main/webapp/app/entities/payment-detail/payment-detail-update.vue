<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2 id="thirdPartyIntegrationApplicationApp.paymentDetail.home.createOrEditLabel" data-cy="PaymentDetailCreateUpdateHeading">
          Create or edit a Payment Detail
        </h2>
        <div>
          <div class="form-group" v-if="paymentDetail.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="paymentDetail.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="payment-detail-computedPaymentDetail">Computed Payment Detail</label>
            <input
              type="text"
              class="form-control"
              name="computedPaymentDetail"
              id="payment-detail-computedPaymentDetail"
              data-cy="computedPaymentDetail"
              :class="{ valid: !v$.computedPaymentDetail.$invalid, invalid: v$.computedPaymentDetail.$invalid }"
              v-model="v$.computedPaymentDetail.$model"
            />
            <div v-if="v$.computedPaymentDetail.$anyDirty && v$.computedPaymentDetail.$invalid">
              <small class="form-text text-danger" v-for="error of v$.computedPaymentDetail.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="payment-detail-apiRequest">Api Request</label>
            <select
              class="form-control"
              id="payment-detail-apiRequest"
              data-cy="apiRequest"
              name="apiRequest"
              v-model="paymentDetail.apiRequest"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  paymentDetail.apiRequest && apiRequestOption.id === paymentDetail.apiRequest.id
                    ? paymentDetail.apiRequest
                    : apiRequestOption
                "
                v-for="apiRequestOption in apiRequests"
                :key="apiRequestOption.id"
              >
                {{ apiRequestOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="payment-detail-operation">Operation</label>
            <select
              class="form-control"
              id="payment-detail-operation"
              data-cy="operation"
              name="operation"
              v-model="paymentDetail.operation"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  paymentDetail.operation && operationOption.id === paymentDetail.operation.id ? paymentDetail.operation : operationOption
                "
                v-for="operationOption in operations"
                :key="operationOption.id"
              >
                {{ operationOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="v$.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./payment-detail-update.component.ts"></script>
