<template>
  <div>
    <h2 id="page-heading" data-cy="RequestInputHeading">
      <span id="request-input-heading">Request Inputs</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh list</span>
        </button>
        <router-link :to="{ name: 'RequestInputCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-request-input"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span>Create a new Request Input</span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && requestInputs && requestInputs.length === 0">
      <span>No Request Inputs found</span>
    </div>
    <div class="table-responsive" v-if="requestInputs && requestInputs.length > 0">
      <table class="table table-striped" aria-describedby="requestInputs">
        <thead>
          <tr>
            <th scope="row"><span>ID</span></th>
            <th scope="row"><span>Input Name</span></th>
            <th scope="row"><span>Input Type</span></th>
            <th scope="row"><span>Data Type</span></th>
            <th scope="row"><span>Test Value</span></th>
            <th scope="row"><span>Default Value</span></th>
            <th scope="row"><span>Auto User Value</span></th>
            <th scope="row"><span>Is Encoded</span></th>
            <th scope="row"><span>Max Length</span></th>
            <th scope="row"><span>Min Length</span></th>
            <th scope="row"><span>Min Value</span></th>
            <th scope="row"><span>Max Value</span></th>
            <th scope="row"><span>Validation Pattern</span></th>
            <th scope="row"><span>Is Required</span></th>
            <th scope="row"><span>Value From Transaction</span></th>
            <th scope="row"><span>Operation</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="requestInput in requestInputs" :key="requestInput.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'RequestInputView', params: { requestInputId: requestInput.id } }">{{
                requestInput.id
              }}</router-link>
            </td>
            <td>{{ requestInput.inputName }}</td>
            <td>{{ requestInput.inputType }}</td>
            <td>{{ requestInput.dataType }}</td>
            <td>{{ requestInput.testValue }}</td>
            <td>{{ requestInput.defaultValue }}</td>
            <td>{{ requestInput.autoUserValue }}</td>
            <td>{{ requestInput.isEncoded }}</td>
            <td>{{ requestInput.maxLength }}</td>
            <td>{{ requestInput.minLength }}</td>
            <td>{{ requestInput.minValue }}</td>
            <td>{{ requestInput.maxValue }}</td>
            <td>{{ requestInput.validationPattern }}</td>
            <td>{{ requestInput.isRequired }}</td>
            <td>{{ requestInput.valueFromTransaction }}</td>
            <td>
              <div v-if="requestInput.operation">
                <router-link :to="{ name: 'OperationView', params: { operationId: requestInput.operation.id } }">{{
                  requestInput.operation.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'RequestInputView', params: { requestInputId: requestInput.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'RequestInputEdit', params: { requestInputId: requestInput.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(requestInput)"
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
        <span id="thirdPartyIntegrationApplicationApp.requestInput.delete.question" data-cy="requestInputDeleteDialogHeading"
          >Confirm delete operation</span
        >
      </template>
      <div class="modal-body">
        <p id="jhi-delete-requestInput-heading">Are you sure you want to delete Request Input {{ removeId }}?</p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-requestInput"
            data-cy="entityConfirmDeleteButton"
            v-on:click="removeRequestInput()"
          >
            Delete
          </button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./request-input.component.ts"></script>
